package cn.topland.controller;

import cn.topland.dto.converter.PermissionConverter;
import cn.topland.service.PermissionService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private PermissionService service;

    @Autowired
    private PermissionConverter converter;

    @GetMapping("/defaults")
    public Response defaults() {

        return Responses.success(converter.toPermissionDTOs(service.listDefaultPermissions()));
    }
}