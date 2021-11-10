package cn.topland.dao;

import cn.topland.entity.Quotation;
import cn.topland.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {

    List<Quotation> findByCreatorAndCreateTimeBetween(User creator, LocalDateTime start, LocalDateTime end);
}