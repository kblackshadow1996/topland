package cn.topland.dao;

import cn.topland.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}