package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Brew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrewRepository extends JpaRepository<Brew, Long> {
}
