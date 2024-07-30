package com.example.postgresdemo.controller;

import com.example.postgresdemo.dto.CoffeeSaleResponseDTO;
import com.example.postgresdemo.dto.UserLoyaltyResponseDTO;
import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.CoffeeSale;
import com.example.postgresdemo.model.User;
import com.example.postgresdemo.model.UserLoyalty;
import com.example.postgresdemo.repository.UserLoyaltyRepository;
import com.example.postgresdemo.repository.UserRepository; // Ensure you have a repository for User
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-loyalties")
public class UserLoyaltyController {

    @Autowired
    private UserLoyaltyRepository userLoyaltyRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<UserLoyaltyResponseDTO> getUserLoyaltyByUserId(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        UserLoyalty userLoyalty = userLoyaltyRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("UserLoyalty not found for userId " + userId));

        UserLoyaltyResponseDTO responseDTO = UserLoyaltyResponseDTO.mapToResponseDTO(userLoyalty);

        return ResponseEntity.ok(responseDTO);
    }
}
