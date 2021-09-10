package cn.topland.service;

import cn.topland.dao.RoleRepository;
import cn.topland.entity.Role;
import cn.topland.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public Role add(Role roleInfo, User creator) {

        Role role = create(roleInfo, creator);
        return repository.saveAndFlush(role);
    }

    private Role create(Role roleInfo, User creator) {

        roleInfo.setCreatorId(creator.getUserId());
        roleInfo.setCreator(creator.getName());
        roleInfo.setEditorId(creator.getUserId());
        roleInfo.setEditor(creator.getName());
        return roleInfo;
    }
}