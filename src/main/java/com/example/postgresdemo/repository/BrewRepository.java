package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Brew;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface BrewRepository extends JpaRepository<Brew, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Brew b SET b.statustype = false WHERE b.id = :brewId")
    void softDeleteBrew(Long brewId);

    @Query("SELECT b FROM Brew b WHERE b.id = :brewId AND b.statustype = true")
    Optional<Brew> findActiveBrewById(Long brewId);

    @Query("SELECT b FROM Brew b WHERE b.statustype = true")
    List<Brew> findAllActiveBrews();
}