package cn.topland.service;

import cn.topland.dao.DepartmentRepository;
import cn.topland.dao.UserRepository;
import cn.topland.entity.Department;
import cn.topland.entity.User;
import cn.topland.gateway.WeworkGateway;
import cn.topland.gateway.response.UserInfo;
import cn.topland.gateway.response.WeworkUser;
import cn.topland.service.parser.WeworkUserParser;
import cn.topland.util.AccessException;
import cn.topland.util.InternalException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static cn.topland.entity.User.Source;

@Service
public class UserService {

    @Autowired
    private WeworkGateway weworkGateway;

    @Autowired
    private UserRepository repository;

    @Autowired
    private DepartmentRepository deptRepository;

    @Autowired
    private WeworkUserParser userParser;

    public User get(Long id) {

        return repository.getById(id);
    }

    /**
     * 企业微信登录
     */
    public User loginByWework(String code) throws AccessException, InternalException {

        UserInfo userInfo = weworkGateway.getUserInfo(code);
        if (Objects.nonNull(userInfo)) {

            String userId = userInfo.getUserId();
            User user = repository.findById(userId, Source.WEWORK);
            if (Objects.nonNull(user)) { // 系统存在用户

                return loginByWeworkUser(user);
            }
            throw new InternalException("获取微信用户失败");
        }
        throw new InternalException("获取微信用户信息失败");
    }

    /**
     * 按部门同步(只同步部门直属人员)
     */
    @Transactional
    public List<User> syncWeworkUser(String deptId, User creator) throws Exception {

        // 用于关联用户部门
        List<Department> departments = deptRepository.listAllDeptIds(Department.Source.WEWORK);
        checkIfSyncDept(departments);
        List<WeworkUser> weworkUsers = weworkGateway.listUsers(deptId, false);
        // 企业微信同步的用户
        List<User> users = userParser.parse(weworkUsers);
        // 需要更新的部门用户
        List<User> allUsers = repository.findBySource(Source.WEWORK);
        List<User> deptUsers = getUserOfDepartment(allUsers, deptId);

        List<User> mergeUsers = syncUsers(deptUsers, users, mappingUserDept(weworkUsers, departments), creator);
        return repository.saveAllAndFlush(mergeUsers);
    }

    /**
     * 同步所有
     */
    @Transactional
    public List<User> syncAllWeworkUser(User creator) throws InternalException {

        List<Department> departments = deptRepository.listAllDeptIds(Department.Source.WEWORK);
        checkIfSyncDept(departments);
        List<WeworkUser> weworkUsers = weworkGateway.listUsers(filterTopDept(departments).getDeptId(), true);
        List<User> users = userParser.parse(weworkUsers);
        List<User> persistUsers = repository.findBySource(Source.WEWORK);
        forbiddenResigns(persistUsers, users);

        List<User> mergeUsers = syncUsers(persistUsers, users, mappingUserDept(weworkUsers, departments), creator);
        return repository.saveAllAndFlush(mergeUsers);
    }

    // 如果离职自动被禁用
    private void forbiddenResigns(List<User> persistUsers, List<User> users) {

        Map<String, User> updateUsers = users.stream().collect(Collectors.toMap(User::getUserId, u -> u));
        persistUsers.stream().filter(u -> !updateUsers.containsKey(u.getUserId())).forEach(u -> {

            u.setActive(false);
        });
    }

    // 关联用户部门
    private Map<String, List<Department>> mappingUserDept(List<WeworkUser> weworkUsers, List<Department> departments) {

        Map<String, List<Department>> userDeptMap = new HashMap<>();
        weworkUsers.forEach(user -> {

            List<String> deptIds = user.getDepartment();
            List<Department> userDepartments = departments.stream()
                    .filter(dept -> deptIds.contains(dept.getDeptId())).collect(Collectors.toList());
            userDeptMap.put(user.getUserId(), userDepartments);
        });
        return userDeptMap;
    }

    private List<User> syncUsers(List<User> persistUsers, List<User> users, Map<String, List<Department>> userDeptMap, User creator) {

        Map<String, User> userMap = persistUsers.stream().collect(Collectors.toMap(User::getUserId, u -> u));
        users.forEach(user -> {

            if (userMap.containsKey(user.getUserId())) {

                updateUser(userMap.get(user.getUserId()), user);
            } else {

                userMap.put(user.getUserId(), createUser(user, userDeptMap.get(user.getUserId()), creator));
            }
        });
        return new ArrayList<>(userMap.values());
    }

    private User createUser(User user, List<Department> departments, User creator) {

        user.setCreator(creator);
        user.setEditor(creator);
        user.setDepartments(departments);
        return user;
    }

    private void updateUser(User persistUser, User user) {

        persistUser.setName(user.getName());
        persistUser.setMobile(user.getMobile());
        persistUser.setEmail(user.getEmail());
        persistUser.setAvatar(user.getAvatar());
        persistUser.setExternalPosition(user.getExternalPosition());
        persistUser.setActive(user.getActive() && persistUser.getActive());
        persistUser.setLeadDepartments(user.getLeadDepartments());
    }

    private List<User> getUserOfDepartment(List<User> persistUsers, String deptId) {

        return persistUsers.stream()
                .filter(user -> user.getDepartments().stream().anyMatch(dept -> deptId.equals(dept.getDeptId())))
                .collect(Collectors.toList());
    }

    private Department filterTopDept(List<Department> departments) {

        List<String> deptIds = departments.stream().map(Department::getDeptId).collect(Collectors.toList());
        // 顶层组织没有父组织
        return departments.stream()
                .filter(dept -> !deptIds.contains(dept.getParentDeptId()))
                .findFirst().get();
    }

    private User loginByWeworkUser(User user) throws AccessException {

        if (user.getActive()) { // 启用

            return user;
        } else { // 禁用

            throw new AccessException();
        }
    }

    private void checkIfSyncDept(List<Department> departments) throws InternalException {

        if (CollectionUtils.isEmpty(departments)) {

            throw new InternalException("请先同步组织");
        }
    }
}