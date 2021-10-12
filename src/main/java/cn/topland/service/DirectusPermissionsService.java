package cn.topland.service;

import cn.topland.dao.DirectusPermissionsRepository;
import cn.topland.entity.DirectusPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectusPermissionsService {

    @Autowired
    private DirectusPermissionsRepository repository;

    public List<DirectusPermissions> listRolesPermissions(String role) {

        return repository.findAllByRole(role);
    }
}