package cn.topland.service.parser;

import cn.topland.entity.User;
import cn.topland.gateway.response.WeworkUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.topland.entity.User.Source;

/**
 * 企业微信用户转为系统用户
 */
@Component
public class WeworkUserParser {

    private static final Map<String, List<String>> POSITIONS = new HashMap<>();

    static {

        POSITIONS.put("销售经理", List.of("大客户部经理"));
        POSITIONS.put("销售", List.of("大客户经理", "客户经理"));
        POSITIONS.put("编导", List.of("编导"));
        POSITIONS.put("制片", List.of("制片专员"));
        POSITIONS.put("策划", List.of("策划经理", "策划专员"));
        POSITIONS.put("人像修图师", List.of("高级人像修图师", "中级人像修图师", "初级人像修图师"));
        POSITIONS.put("静物修图师", List.of("高级静物修图师", "中级静物修图师", "初级静物修图师"));
        POSITIONS.put("静物组组长", List.of("静物摄影师组长"));
        POSITIONS.put("摄影师", List.of("人像摄影师", "静物摄影师"));
        POSITIONS.put("摄像师", List.of("摄像师"));
        POSITIONS.put("大助、二助", List.of("人像摄影师助理"));
    }

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
        user.setExternalPosition(weworkUser.getPosition());
        user.setInternalPosition(getInternalPosition(weworkUser.getPosition()));
        user.setEmail(weworkUser.getEmail());
        user.setActive(isActive(weworkUser.getStatus()));
        user.setSource(Source.WEWORK);
        return user;
    }

    private String getInternalPosition(String position) {

        Map.Entry<String, List<String>> positionEntry = POSITIONS.entrySet().stream()
                .filter(en -> en.getValue().contains(position)).findFirst()
                .orElse(null);
        return positionEntry != null ? positionEntry.getKey() : position;
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