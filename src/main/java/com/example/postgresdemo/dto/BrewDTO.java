package com.example.postgresdemo.dto;

import com.example.postgresdemo.model.Brew;

public class BrewDTO {
    private Long id;
    private String name;
    private String description;

    public BrewDTO() {
    }

    public BrewDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    static BrewDTO mapBrewToDTO(Brew brew) {
        BrewDTO brewDTO = new BrewDTO();
        brewDTO.setId(brew.getId());
        brewDTO.setName(brew.getName());
        brewDTO.setDescription(brew.getDescription());
        return brewDTO;
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
}
