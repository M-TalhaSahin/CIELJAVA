package com.example.postgresdemo.dto;

import com.example.postgresdemo.model.CoffeeSale;

import java.time.LocalDateTime;

public class CoffeeSaleResponseDTO {
    private Long id;
    private Long userId;
    private CoffeeDTO coffee;
    private BrewDTO brew;
    private LocalDateTime saleDate;

    public CoffeeSaleResponseDTO() {
    }

    public CoffeeSaleResponseDTO(Long id, Long userId, CoffeeDTO coffee, BrewDTO brew, LocalDateTime saleDate) {
        this.id = id;
        this.userId = userId;
        this.coffee = coffee;
        this.brew = brew;
        this.saleDate = saleDate;
    }

    public static CoffeeSaleResponseDTO mapToResponseDTO(CoffeeSale coffeeSale) {
        CoffeeSaleResponseDTO responseDTO = new CoffeeSaleResponseDTO();
        responseDTO.setId(coffeeSale.getId());
        responseDTO.setUserId(coffeeSale.getUser().getId()); // Set userId from CoffeeSale's User entity

        CoffeeDTO coffeeDTO = CoffeeDTO.mapCoffeeToDTO(coffeeSale.getCoffee());
        responseDTO.setCoffee(coffeeDTO);

        BrewDTO brewDTO = BrewDTO.mapBrewToDTO(coffeeSale.getBrew());
        responseDTO.setBrew(brewDTO);

        responseDTO.setSaleDate(coffeeSale.getSaleDate());
        return responseDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public CoffeeDTO getCoffee() {
        return coffee;
    }

    public void setCoffee(CoffeeDTO coffee) {
        this.coffee = coffee;
    }

    public BrewDTO getBrew() {
        return brew;
    }

    public void setBrew(BrewDTO brew) {
        this.brew = brew;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }
}
