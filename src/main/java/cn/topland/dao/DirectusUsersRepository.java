package cn.topland.dao;

import cn.topland.entity.DirectusUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectusUsersRepository extends JpaRepository<DirectusUsers, String> {

    DirectusUsers findByEmail(String email);
}