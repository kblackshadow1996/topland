package cn.topland.dao;

import cn.topland.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select user from User user where user.userId=?1 and user.source=?2")
    User findById(String userId, User.Source source);

    @Query(value = "select user from User user where user.source=?1")
    List<User> findBySource(User.Source source);
}