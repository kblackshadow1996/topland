package cn.topland.service;

import cn.topland.dao.PermissionRepository;
import cn.topland.entity.SimpleIdEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository repository;

    public List<Long> distinct(List<Long> ids) {

        return repository.findByIdIn(ids).stream()
                .distinct()
                .map(SimpleIdEntity::getId).collect(Collectors.toList());
    }
}