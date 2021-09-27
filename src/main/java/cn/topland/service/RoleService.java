package cn.topland.service;

import cn.topland.dao.AuthorityRepository;
import cn.topland.dao.RoleRepository;
import cn.topland.entity.Authority;
import cn.topland.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private AuthorityRepository authRepository;

    public Role get(Long id) {

        return repository.getById(id);
    }

    public Role auth(Long roleId, List<Long> auths) {

        Role role = repository.getById(roleId);
        List<Authority> authorities = authRepository.findAllById(auths);
        role.setAuthorities(authorities);

        return repository.saveAndFlush(role);
    }
}