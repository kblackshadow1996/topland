package cn.topland.controller;

import cn.topland.dto.composer.PermissionComposer;
import cn.topland.entity.Authority;
import cn.topland.entity.Role;
import cn.topland.service.AuthorityService;
import cn.topland.service.RoleService;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PermissionComposer composer;

    @GetMapping("/auth")
    public Response auth(Long roleId, @RequestParam("auths") List<Long> auths) {

        Role role = roleService.get(roleId);
        return Responses.success(composer.compose(getAuths(role), authorityService.findByIds(auths), role.getRole().getId()));
    }

    private List<Authority> getAuths(Role role) {

        return CollectionUtils.isEmpty(role.getAuthorities())
                ? List.of()
                : role.getAuthorities();
    }
}