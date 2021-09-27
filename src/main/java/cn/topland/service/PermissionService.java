package cn.topland.service;

import cn.topland.dao.PermissionRepository;
import cn.topland.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository repository;

    public List<Permission> listDefaultPermissions() {

        return repository.listDefaultPermissions();
    }
}