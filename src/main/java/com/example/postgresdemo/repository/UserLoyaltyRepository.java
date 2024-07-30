package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.UserLoyalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoyaltyRepository extends JpaRepository<UserLoyalty, Long> {
    Optional<UserLoyalty> findByUserId(Long userId);
}
