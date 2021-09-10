package cn.topland.controller;

import cn.topland.gateway.WeworkGateway;
import cn.topland.gateway.response.WeworkDepartment;
import cn.topland.gateway.response.WeworkUser;
import cn.topland.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @Autowired
    private WeworkGateway weworkGateway;

    @GetMapping("/heartbeat")
    public String heartbeat() {

        return "hello world!";
    }

    @GetMapping("/user/list")
    public String list(String deptId) {

        List<WeworkUser> weworkUsers = weworkGateway.listUsers(deptId, true);
        return JsonUtils.toJson(weworkUsers);
    }

    @GetMapping("/user/simplelist")
    public String simpleList(String deptId) {

        List<WeworkUser> weworkUsers = weworkGateway.simpleListUsers(deptId, true);
        return JsonUtils.toJson(weworkUsers);
    }

    @GetMapping("/department/list/xx")
    public String listDepartments(@RequestParam(required = false) String deptId) {

        List<WeworkDepartment> weworkDepartments = weworkGateway.listDepartments(deptId);
        return JsonUtils.toJson(weworkDepartments);
    }
}