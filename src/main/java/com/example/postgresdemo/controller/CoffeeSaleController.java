package com.example.postgresdemo.controller;

import com.example.postgresdemo.dto.CoffeeSaleResponseDTO;
import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Brew;
import com.example.postgresdemo.model.Coffee;
import com.example.postgresdemo.model.CoffeeSale;
import com.example.postgresdemo.model.User;
import com.example.postgresdemo.repository.CoffeeSaleRepository;
import com.example.postgresdemo.repository.CoffeeRepository;
import com.example.postgresdemo.repository.BrewRepository;
import com.example.postgresdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/coffee-sales")
public class CoffeeSaleController {

    @Autowired
    private CoffeeSaleRepository coffeeSaleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private BrewRepository brewRepository;

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

    // Get a specific coffee sale by ID
    @GetMapping("/{coffeeSaleId}")
    public CoffeeSale getCoffeeSaleById(@PathVariable Long coffeeSaleId) {
        return coffeeSaleRepository.findById(coffeeSaleId)
                .orElseThrow(() -> new ResourceNotFoundException("Coffee sale not found with id " + coffeeSaleId));
    }

    // Create a new coffee sale
    @PostMapping
    public ResponseEntity<CoffeeSaleResponseDTO> createCoffeeSale(@Valid @RequestBody CoffeeSale coffeeSale) {
        try {
            // Check if the referenced entities (User, Brew, Coffee) exist
            User user = userRepository.findById(coffeeSale.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + coffeeSale.getUser().getId()));

            Coffee coffee = coffeeRepository.findById(coffeeSale.getCoffee().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Coffee not found with id " + coffeeSale.getCoffee().getId()));

            Brew brew = brewRepository.findById(coffeeSale.getBrew().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brew not found with id " + coffeeSale.getBrew().getId()));

            // Set the validated entities and save CoffeeSale
            coffeeSale.setUser(user);
            coffeeSale.setCoffee(coffee);
            coffeeSale.setBrew(brew);
            coffeeSale.setSaleDate(LocalDateTime.now()); // Set current timestamp as sale date

            CoffeeSale savedCoffeeSale = coffeeSaleRepository.save(coffeeSale);

            // Map CoffeeSale to CoffeeSaleResponseDTO and return
            CoffeeSaleResponseDTO responseDTO = CoffeeSaleResponseDTO.mapToResponseDTO(savedCoffeeSale);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // Update an existing coffee sale
    @PutMapping("/{coffeeSaleId}")
    public CoffeeSale updateCoffeeSale(@PathVariable Long coffeeSaleId,
                                       @Valid @RequestBody CoffeeSale coffeeSaleRequest) {
        return coffeeSaleRepository.findById(coffeeSaleId)
                .map(coffeeSale -> {
                    coffeeSale.setUser(userRepository.findById(coffeeSaleRequest.getUser().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + coffeeSaleRequest.getUser().getId())));
                    coffeeSale.setCoffee(coffeeRepository.findById(coffeeSaleRequest.getCoffee().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Coffee not found with id " + coffeeSaleRequest.getCoffee().getId())));
                    coffeeSale.setBrew(brewRepository.findById(coffeeSaleRequest.getBrew().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Brew not found with id " + coffeeSaleRequest.getBrew().getId())));
                    coffeeSale.setSaleDate(LocalDateTime.now()); // Update sale date
                    return coffeeSaleRepository.save(coffeeSale);
                }).orElseThrow(() -> new ResourceNotFoundException("Coffee sale not found with id " + coffeeSaleId));
    }

    // Delete a coffee sale
    @DeleteMapping("/{coffeeSaleId}")
    public ResponseEntity<?> deleteCoffeeSale(@PathVariable Long coffeeSaleId) {
        return coffeeSaleRepository.findById(coffeeSaleId)
                .map(coffeeSale -> {
                    coffeeSaleRepository.delete(coffeeSale);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Coffee sale not found with id " + coffeeSaleId));
    }
}
