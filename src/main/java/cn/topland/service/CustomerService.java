package cn.topland.service;

import cn.topland.dao.ContractRepository;
import cn.topland.dao.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private ContractRepository contractRepository;
}