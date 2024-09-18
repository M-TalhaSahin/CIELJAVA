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

        UserLoyalty userLoyalty = userLoyaltyRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("UserLoyalty not found for userId " + userId));

        int numberOfCoffeeToDisplay = userLoyalty.getProgressBar();

        Pageable pageable = PageRequest.of(0, numberOfCoffeeToDisplay);

        List<CoffeeSale> coffeeSales = coffeeSaleRepository.findLatestByUserId(userId, pageable);

        return coffeeSales.stream().map(CoffeeSaleResponseDTO::mapToResponseDTO).collect(Collectors.toList());
    }

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
