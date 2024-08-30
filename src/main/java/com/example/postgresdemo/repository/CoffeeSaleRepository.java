package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.CoffeeSale;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoffeeSaleRepository extends JpaRepository<CoffeeSale, Long> {
    List<CoffeeSale> findByUserId(Long userId);
    @Query("SELECT cs FROM CoffeeSale cs WHERE cs.user.id = :userId AND cs.isFree = false ORDER BY cs.saleDate DESC")
    List<CoffeeSale> findLatestByUserId(@Param("userId") Long userId, Pageable pageable);
}
