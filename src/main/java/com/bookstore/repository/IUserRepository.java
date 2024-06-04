package com.bookstore.repository;

import com.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUserName(String username);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_role (user_id, role_id) VALUES (?1, ?2)", nativeQuery = true)
    void addRoleToUser(Long userId, Long roleId);


    @Query("SELECT u.id FROM User u WHERE u.name = ?1")
    Long getUserIdByUserName(String userName);

    @Query(value = "SELECT r.name FROM Role r INNER JOIN user_role ur ON r.id = ur.role_id WHERE ur.user_id = ?1",
            nativeQuery = true)
    String[] getRoleOfUser(Long userId);

    Optional<User> findByEmail(String email);
}
