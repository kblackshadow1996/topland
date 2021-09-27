package cn.topland.controller;

import cn.topland.entity.User;
import cn.topland.service.DepartmentService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import cn.topland.util.annotation.bind.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/sync/wework/all")
    public Response syncAllWeworkDept() {

        return Responses.success(departmentService.syncAllWeworkDept());
    }

    @GetMapping("/sync/wework/{deptId}")
    public Response syncWeworkDept(@PathVariable String deptId) {

        return Responses.success(departmentService.syncWeworkDept(deptId));
    }
}