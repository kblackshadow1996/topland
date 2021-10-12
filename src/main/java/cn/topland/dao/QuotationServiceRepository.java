package cn.topland.dao;

import cn.topland.entity.QuotationService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationServiceRepository extends JpaRepository<QuotationService, Long> {
}