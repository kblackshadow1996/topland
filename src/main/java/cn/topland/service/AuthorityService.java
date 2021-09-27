package cn.topland.service;

import cn.topland.dao.AuthorityRepository;
import cn.topland.entity.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository repository;

    public List<Authority> findByIds(List<Long> ids) {

        return repository.findAllById(ids);
    }
}