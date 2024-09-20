package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.statustype = false WHERE u.id = :userId")
    void softDeleteUser(Long userId);

    @Query("SELECT u FROM User u WHERE u.id = :userId AND u.statustype = true")
    Optional<User> findActiveUserById(Long userId);

    @Query("SELECT u FROM User u WHERE u.statustype = true")
    List<User> findAllActiveUsers();
}