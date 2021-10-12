package cn.topland.dao;

import cn.topland.entity.Quotation;
import cn.topland.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {

    Long countByCreatorAndCreateTimeBetween(User creator, LocalDateTime start, LocalDateTime end);
}