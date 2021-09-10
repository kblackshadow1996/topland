package cn.topland.service.parser;

import cn.topland.entity.User;
import cn.topland.gateway.response.WeworkUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 企业微信用户转为系统用户
 */
@Component
public class UserParser {

    public List<User> parse(List<WeworkUser> weworkUsers) {

        return weworkUsers.stream().map(this::parse).collect(Collectors.toList());
    }

    public User parse(WeworkUser weworkUser) {

        User user = new User();
        user.setUserId(weworkUser.getUserId());
        user.setName(weworkUser.getName());
        user.setAvatar(weworkUser.getAvatar());
        user.setLeadDepartments(getLeadDepartments(weworkUser));
        user.setMobile(weworkUser.getMobile());
        user.setPosition(weworkUser.getPosition());
        user.setEmail(weworkUser.getEmail());
        user.setActive(isActive(weworkUser.getStatus()));
        return user;
    }

    /**
     * 部门id：[1,2]，负责人：[1,0]，则负责部门为1
     */
    private String getLeadDepartments(WeworkUser weworkUser) {

        List<String> isLeaderInDept = weworkUser.getIsLeaderInDept();
        List<String> departments = weworkUser.getDepartment();
        List<String> leadingDepartments = new ArrayList<>();
        for (int i = 0; i < departments.size() - 1; i++) {

            if (isLeadingDept(isLeaderInDept.get(i))) {

                leadingDepartments.add(departments.get(i));
            }
        }
        return String.join(",", leadingDepartments);
    }

    /**
     * @param isLeaderInDept 0和1，1表示为负责人
     */
    private boolean isLeadingDept(String isLeaderInDept) {

        return "1".equals(isLeaderInDept);
    }

    /**
     * @param status 企业微信用户共有4种状态，1=已激活，2=已禁用，4=未激活，5=退出企业
     */
    private boolean isActive(String status) {

        return "1".equals(status);
    }
}