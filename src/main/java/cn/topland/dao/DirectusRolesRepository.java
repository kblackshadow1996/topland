package cn.topland.dao;

import cn.topland.entity.DirectusRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectusRolesRepository extends JpaRepository<DirectusRoles, String> {

    DirectusRoles getByName(String name);
}