package com.example.postgresdemo.controller;

import com.example.postgresdemo.dto.CoffeeSaleResponseDTO;
import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.*;
import com.example.postgresdemo.repository.*;
import com.example.postgresdemo.service.UserLoyaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/coffee-sales")
@CrossOrigin(origins = "http://localhost:3000")
public class CoffeeSaleController {

    @Autowired
    private CoffeeSaleRepository coffeeSaleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private BrewRepository brewRepository;

    @Autowired
    private UserLoyaltyService userLoyaltyService;

    @Autowired
    private UserLoyaltyRepository userLoyaltyRepository;

    // Get all coffee sales
    @GetMapping
    public List<CoffeeSale> getAllCoffeeSales() {
        return coffeeSaleRepository.findAll();
    }

    @GetMapping("/by-user/{userId}")
    public List<CoffeeSaleResponseDTO> getCoffeeSalesByUserId(@PathVariable Long userId) {
        List<CoffeeSale> coffeeSales = coffeeSaleRepository.findByUserId(userId);
        return coffeeSales.stream().map(CoffeeSaleResponseDTO::mapToResponseDTO).collect(Collectors.toList());
    }

    @GetMapping("/to-display/by-user/{userId}")
    public List<CoffeeSaleResponseDTO> getLastCoffeeSalesByUserId(@PathVariable Long userId) {

        // Fetch the UserLoyalty object
        UserLoyalty userLoyalty = userLoyaltyRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("UserLoyalty not found for userId " + userId));

        // Get the number of coffee sales to display from UserLoyalty
        int numberOfCoffeeToDisplay = userLoyalty.getProgressBar();

        // Define the Pageable object with limit and sorting
        Pageable pageable = PageRequest.of(0, numberOfCoffeeToDisplay);

        // Fetch the latest 'numberOfCoffeeToDisplay' coffee sales by userId
        List<CoffeeSale> coffeeSales = coffeeSaleRepository.findLatestByUserId(userId, pageable);

        // Map to DTO and return the list
        return coffeeSales.stream().map(CoffeeSaleResponseDTO::mapToResponseDTO).collect(Collectors.toList());
    }

    // Create a new coffee sale
    @PostMapping
    public ResponseEntity<CoffeeSaleResponseDTO> createCoffeeSale(@Valid @RequestBody CoffeeSale coffeeSale) {
        try {
            User user = userRepository.findById(coffeeSale.getUser().getId()).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + coffeeSale.getUser().getId()));

            Coffee coffee = coffeeRepository.findById(coffeeSale.getCoffee().getId()).orElseThrow(() -> new ResourceNotFoundException("Coffee not found with id " + coffeeSale.getCoffee().getId()));

            Brew brew = brewRepository.findById(coffeeSale.getBrew().getId()).orElseThrow(() -> new ResourceNotFoundException("Brew not found with id " + coffeeSale.getBrew().getId()));

            coffeeSale.setUser(user);
            coffeeSale.setCoffee(coffee);
            coffeeSale.setBrew(brew);
            coffeeSale.setSaleDate(LocalDateTime.now());

            CoffeeSale savedCoffeeSale = coffeeSaleRepository.save(coffeeSale);

            CoffeeSaleResponseDTO responseDTO = CoffeeSaleResponseDTO.mapToResponseDTO(savedCoffeeSale);

            userLoyaltyService.updateLoyaltyForSale(user.getId(), coffeeSale.isFree());

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
