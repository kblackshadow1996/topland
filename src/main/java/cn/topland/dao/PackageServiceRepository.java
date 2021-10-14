package cn.topland.dao;

import cn.topland.entity.PackageService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageServiceRepository extends JpaRepository<PackageService, Long> {
}