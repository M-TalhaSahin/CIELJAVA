package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Coffee;
import com.example.postgresdemo.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/coffees")
public class CoffeeController {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @GetMapping
    public List<Coffee> getAllCoffees() {
        return coffeeRepository.findAll();
    }

    @GetMapping("/{coffeeId}")
    public Coffee getCoffeeById(@PathVariable Long coffeeId) {
        return coffeeRepository.findById(coffeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Coffee not found with id " + coffeeId));
    }

    @PostMapping
    public Coffee createCoffee(@Valid @RequestBody Coffee coffee) {
        return coffeeRepository.save(coffee);
    }

    @PutMapping("/{coffeeId}")
    public Coffee updateCoffee(@PathVariable Long coffeeId,
                               @Valid @RequestBody Coffee coffeeRequest) {
        return coffeeRepository.findById(coffeeId)
                .map(coffee -> {
                    coffee.setName(coffeeRequest.getName());
                    coffee.setDescription(coffeeRequest.getDescription());
                    coffee.setBody(coffeeRequest.getBody());
                    coffee.setAcidity(coffeeRequest.getAcidity());
                    return coffeeRepository.save(coffee);
                }).orElseThrow(() -> new ResourceNotFoundException("Coffee not found with id " + coffeeId));
    }

    @DeleteMapping("/{coffeeId}")
    public ResponseEntity<?> deleteCoffee(@PathVariable Long coffeeId) {
        return coffeeRepository.findById(coffeeId)
                .map(coffee -> {
                    coffeeRepository.delete(coffee);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Coffee not found with id " + coffeeId));
    }
}
