package cn.topland.service;

import cn.topland.dao.AuthorityRepository;
import cn.topland.dao.RoleRepository;
import cn.topland.entity.Authority;
import cn.topland.entity.Role;
import cn.topland.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private AuthorityRepository authRepository;

    public Role add(Role roleInfo, List<Long> authIds, User creator) {

        List<Authority> authorities = authRepository.findAllById(authIds);
        Role role = create(roleInfo, authorities, creator);
        return repository.saveAndFlush(role);
    }

    private Role create(Role roleInfo, List<Authority> auths, User creator) {

        roleInfo.setAuths(auths);
        roleInfo.setCreatorId(creator.getUserId());
        roleInfo.setCreator(creator.getName());
        roleInfo.setEditorId(creator.getUserId());
        roleInfo.setEditor(creator.getName());
        return roleInfo;
    }
}