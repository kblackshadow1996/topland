package cn.topland.service;

import cn.topland.dao.RoleRepository;
import cn.topland.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public Role get(Long id) {

        return repository.getById(id);
    }
}