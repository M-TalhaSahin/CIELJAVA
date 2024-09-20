package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Brew;
import com.example.postgresdemo.model.Coffee;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Coffee c SET c.statustype = false WHERE c.id = :coffeeId")
    void softDeleteCoffee(Long coffeeId);

    @Query("SELECT c FROM Coffee c WHERE c.id = :coffeeId AND c.statustype = true")
    Optional<Coffee> findActiveCoffeeById(Long coffeeId);

    @Query("SELECT c FROM Coffee c WHERE c.statustype = true")
    List<Coffee> findAllActiveCoffees();


}