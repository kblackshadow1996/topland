package cn.topland.dao;

import cn.topland.entity.ExceptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionTypeRepository extends JpaRepository<ExceptionType, Long> {
}