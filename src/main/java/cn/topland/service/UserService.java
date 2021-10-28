package cn.topland.service;

import cn.topland.dao.DepartmentRepository;
import cn.topland.dao.DirectusUsersRepository;
import cn.topland.dao.RoleRepository;
import cn.topland.dao.UserRepository;
import cn.topland.dao.gateway.UserGateway;
import cn.topland.dao.gateway.UsersGateway;
import cn.topland.entity.*;
import cn.topland.entity.directus.UserDO;
import cn.topland.gateway.WeworkGateway;
import cn.topland.gateway.response.UserInfo;
import cn.topland.gateway.response.WeworkUser;
import cn.topland.service.parser.WeworkUserParser;
import cn.topland.util.exception.AccessException;
import cn.topland.util.exception.ExternalException;
import cn.topland.util.exception.InternalException;
import cn.topland.util.exception.QueryException;
import cn.topland.vo.UserVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static cn.topland.entity.User.Source;

@Service
public class UserService {

    @Value("${directus.root.email}")
    private String ROOT_EMAIL;

    @Value("${directus.root.password}")
    private String ROOT_PASSWORD;

    @Autowired
    private WeworkGateway weworkGateway;

    @Autowired
    private UserRepository repository;

    @Autowired
    private DirectusUsersRepository directusUsersRepository;

    @Autowired
    private DepartmentRepository deptRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private WeworkUserParser userParser;

    @Autowired
    private UserGateway userGateway;

    @Autowired
    private UsersGateway usersGateway;

    public User get(Long id) {

        if (id == null || !repository.existsById(id)) {

            throw new QueryException("用户[id:" + id + "]不存在");
        }
        return repository.getById(id);
    }

    public List<User> list(List<Long> ids) {

        return repository.findAllById(ids);
    }

    /**
     * 企业微信登录
     */
    public UserDO loginByWework(String code) {

        UserInfo userInfo = weworkGateway.getUserInfo(code);
        if (Objects.nonNull(userInfo)) {

            String userId = userInfo.getUserId();
            User user = repository.findById(userId, Source.WEWORK);
            if (Objects.nonNull(user)) { // 系统存在用户

                return loginByWeworkUser(user);
            }
            throw new AccessException("该用户未同步到系统,请同步后重试");
        }
        throw new ExternalException("企业微信服务异常");
    }

    /**
     * 按部门同步(只同步部门直属人员)
     */
    public List<UserDO> syncWeworkUser(String deptId, User creator) {

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
        return userGateway.saveAll(mergeUsers, creator.getAccessToken());
    }

    /**
     * 同步所有
     */
    public List<UserDO> syncAllWeworkUser(User creator) {

        List<Department> departments = deptRepository.listAllDeptIds(Department.Source.WEWORK);
        checkIfSyncDept(departments);
        List<WeworkUser> weworkUsers = weworkGateway.listUsers(filterTopDept(departments).getDeptId(), true);
        List<User> users = userParser.parse(weworkUsers);
        List<User> persistUsers = repository.findBySource(Source.WEWORK);
        forbiddenResigns(persistUsers, users);

        List<User> mergeUsers = syncUsers(persistUsers, users, mappingUserDept(weworkUsers, departments), creator);
        return userGateway.saveAll(mergeUsers, creator.getAccessToken());
    }

    // 授权
    public UserDO auth(Long id, UserVO userVO) {

        User creator = get(userVO.getCreator());
        Role role = getRole(userVO.getRole());
        User user = authUser(get(id), userVO, role, creator);
        user.setDirectusUser(authDirectusUser(user.getDirectusUser(), role.getRole(), creator.getAccessToken()));
        return userGateway.auth(List.of(user), creator.getAccessToken()).get(0);
    }

    public List<UserDO> auth(UserVO userVO) {

        User creator = get(userVO.getCreator());
        return userGateway.auth(authUsers(userVO, creator), creator.getAccessToken());
    }

    private List<User> authUsers(UserVO userVO, User creator) throws InternalException {

        List<User> users = repository.findAllById(userVO.getUsers());
        Role role = roleRepository.getById(userVO.getRole());
        combineWithDirectus(users, role.getRole(), creator.getAccessToken());
        return users.stream().map(user -> authUser(user, userVO, role, creator)).collect(Collectors.toList());
    }

    private User authUser(User user, UserVO userVO, Role role, User creator) {

        user.setAuth(userVO.getAuth());
        user.setRole(role);
        user.setEditor(creator);
        user.setLastUpdateTime(LocalDateTime.now());
        return user;
    }

    private DirectusUsers authDirectusUser(DirectusUsers directusUser, DirectusRoles role, String accessToken) {

        directusUser.setRole(role);
        usersGateway.auth(directusUser, accessToken);
        return directusUsersRepository.getById(directusUser.getId());
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

    private List<User> syncUsers(List<User> persistUsers, List<User> users, Map<String, List<Department>> userDeptMap, User creator)
            throws InternalException {

        Map<String, User> userMap = persistUsers.stream().collect(Collectors.toMap(User::getUserId, u -> u));
        // 系统初始化创建的管理员用户
        DirectusUsers root = directusUsersRepository.findByEmail(ROOT_EMAIL);
        users.forEach(user -> {

            if (userMap.containsKey(user.getUserId())) {

                updateUser(userMap.get(user.getUserId()), user);
            } else {

                userMap.put(user.getUserId(), createUser(user, userDeptMap.get(user.getUserId()), creator, root));
            }
        });

        List<User> mergeUsers = new ArrayList<>(userMap.values());
        combineWithDirectus(mergeUsers, null, creator.getAccessToken());
        return mergeUsers;
    }

    private void combineWithDirectus(List<User> users, DirectusRoles role, String accessToken) {

        if (CollectionUtils.isNotEmpty(users)) {

            List<DirectusUsers> directusUsers = listDirectusUsers(users).stream().peek(directusUser -> directusUser.setRole(role)).collect(Collectors.toList());
            Map<String, DirectusUsers> directusUsersMap = usersGateway.saveAll(directusUsers, accessToken).stream()
                    .collect(Collectors.toMap(DirectusUsers::getEmail, u -> u));
            users.forEach(user -> {

                user.setDirectusUser(directusUsersMap.get(user.getDirectusEmail()));
            });
        }
    }

    private List<DirectusUsers> listDirectusUsers(Collection<User> users) {

        return users.stream().map(User::getDirectusUser).collect(Collectors.toList());
    }

    private User createUser(User user, List<Department> departments, User creator, DirectusUsers root) {

        user.setCreator(creator);
        user.setEditor(creator);
        user.setDirectusPassword(ROOT_PASSWORD);
        String directusEmail = user.generateEmail();
        user.setDirectusEmail(directusEmail);
        user.setDirectusUser(createDirectusUser(root, directusEmail));
        user.setDepartments(departments);
        return user;
    }

    private DirectusUsers createDirectusUser(DirectusUsers root, String email) {

        DirectusUsers persistUser = directusUsersRepository.findByEmail(email);
        if (persistUser != null) {
            // 避免创建directus用户成功，创建系统用户失败，导致下次同步再创建directus用户报错
            return persistUser;
        }
        DirectusUsers users = new DirectusUsers();
        users.setId(null);
        users.setEmail(email);
        users.setPassword(root.getPassword());
        return users;
    }

    private void updateUser(User persistUser, User user) {

        persistUser.setName(user.getName());
        persistUser.setMobile(user.getMobile());
        persistUser.setEmail(user.getEmail());
        persistUser.setAvatar(user.getAvatar());
        persistUser.setExternalPosition(user.getExternalPosition());
        persistUser.setInternalPosition(user.getInternalPosition());
        persistUser.setActive(user.getActive() && persistUser.getActive());
        persistUser.setLeadDepartments(user.getLeadDepartments());
    }

    private List<User> getUserOfDepartment(List<User> persistUsers, String deptId) {

        return persistUsers.stream()
                .filter(user -> user.getDepartments().stream().anyMatch(dept -> deptId.equals(dept.getDeptId())))
                .collect(Collectors.toList());
    }

    private Department filterTopDept(List<Department> departments) {

        // 顶层组织没有父组织
        return departments.stream()
                .filter(dept -> dept.getParent() == null)
                .findFirst().get();
    }

    private UserDO loginByWeworkUser(User user) {

        if (user.getActive()) { // 启用

            // TODO directus登录设置token
            return userGateway.login(user);
        } else { // 禁用

            throw new AccessException("该用户已被禁用,请联系管理员");
        }
    }

    private void checkIfSyncDept(List<Department> departments) {

        if (CollectionUtils.isEmpty(departments)) {

            throw new QueryException("请先同步组织");
        }
    }

    private Role getRole(Long role) {

        if (role == null || !roleRepository.existsById(role)) {

            throw new QueryException("角色不存在");
        }
        return roleRepository.getById(role);
    }
}