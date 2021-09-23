package cn.topland.dao;

import cn.topland.entity.DirectusPermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<DirectusPermissions, Long> {

    List<DirectusPermissions> findByIdIn(List<Long> ids);
}