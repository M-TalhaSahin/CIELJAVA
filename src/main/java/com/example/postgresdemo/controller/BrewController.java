package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Brew;
import com.example.postgresdemo.repository.BrewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/brews")
public class BrewController {

    @Autowired
    private BrewRepository brewRepository;

    @GetMapping
    public List<Brew> getAllBrews() {
        return brewRepository.findAllActiveBrews();
    }

    @GetMapping("/{brewId}")
    public Brew getBrewById(@PathVariable Long brewId) {
        return brewRepository.findActiveBrewById(brewId)
                .orElseThrow(() -> new ResourceNotFoundException("Brew not found with id " + brewId));
    }

    @PostMapping
    public Brew createBrew(@Valid @RequestBody Brew brew) {
        return brewRepository.save(brew);
    }

    @PutMapping("/{brewId}")
    public Brew updateBrew(@PathVariable Long brewId,
                           @Valid @RequestBody Brew brewRequest) {
        return brewRepository.findActiveBrewById(brewId)
                .map(brew -> {
                    brew.setName(brewRequest.getName());
                    brew.setDescription(brewRequest.getDescription());
                    return brewRepository.save(brew);
                }).orElseThrow(() -> new ResourceNotFoundException("Brew not found with id " + brewId));
    }

    @DeleteMapping("/{brewId}")
    public ResponseEntity<?> deleteBrew(@PathVariable Long brewId) {
        return brewRepository.findActiveBrewById(brewId)
                .map(brew -> {
                    brewRepository.softDeleteBrew(brew.getId());
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Brew not found with id " + brewId));
    }
}