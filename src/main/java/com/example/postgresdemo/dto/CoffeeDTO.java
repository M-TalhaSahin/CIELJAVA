package com.example.postgresdemo.dto;

import com.example.postgresdemo.model.Coffee;

public class CoffeeDTO {
    private Long id;
    private String name;
    private String description;
    private int body;
    private int acidity;

    public CoffeeDTO() {
    }

    public CoffeeDTO(Long id, String name, String description, int body, int acidity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.body = body;
        this.acidity = acidity;
    }

    public static CoffeeDTO mapCoffeeToDTO(Coffee coffee) {
        CoffeeDTO coffeeDTO = new CoffeeDTO();
        coffeeDTO.setId(coffee.getId());
        coffeeDTO.setName(coffee.getName());
        coffeeDTO.setDescription(coffee.getDescription());
        coffeeDTO.setBody(coffee.getBody());
        coffeeDTO.setAcidity(coffee.getAcidity());
        return coffeeDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBody() {
        return body;
    }

    public void setBody(int body) {
        this.body = body;
    }

    public int getAcidity() {
        return acidity;
    }

    public void setAcidity(int acidity) {
        this.acidity = acidity;
    }
}
