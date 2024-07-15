package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.CoffeeSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoffeeSaleRepository extends JpaRepository<CoffeeSale, Long> {
    List<CoffeeSale> findByUserId(Long userId);
}
