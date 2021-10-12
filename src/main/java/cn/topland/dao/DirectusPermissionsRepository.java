package cn.topland.dao;

import cn.topland.entity.DirectusPermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectusPermissionsRepository extends JpaRepository<DirectusPermissions, String> {

    List<DirectusPermissions> findAllByRole(String role);
}