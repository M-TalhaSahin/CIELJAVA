package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.User;
import com.example.postgresdemo.model.UserLoyalty;
import com.example.postgresdemo.repository.UserLoyaltyRepository;
import com.example.postgresdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserLoyaltyRepository userLoyaltyRepository;

  @GetMapping
  public List<User> getAllUsers() {
    return userRepository.findAllActiveUsers();
  }

  @GetMapping("/{userId}")
  public User getUserById(@PathVariable Long userId) {
    return userRepository.findActiveUserById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
  }

  @GetMapping("/email/{email}")
  public User getUserByEmail(@PathVariable String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));
  }

  @PostMapping
  public ResponseEntity<?> createUser(@RequestBody User user) {
    try {
      // Provider değerinin geçerli olup olmadığını kontrol edin (opsiyonel)
      if (!isValidProvider(user.getProvider())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid provider value");
      }

      User createdUser = userRepository.save(user);
      return ResponseEntity.ok(createdUser);
    } catch (Exception e) {
      // Hatanın detaylarını loglamak için
      e.printStackTrace();
      // Hata mesajını daha ayrıntılı bir şekilde geri döndürmek için
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
    }
  }

  @PutMapping("/{userId}")
  public User updateUser(@PathVariable Long userId, @Valid @RequestBody User userRequest) {
    return userRepository.findActiveUserById(userId)
        .map(user -> {
          user.setEmail(userRequest.getEmail());
          user.setFullname(userRequest.getFullname());
          user.setProvider(userRequest.getProvider()); // Provider alanını güncelle
          user.setStatusType(userRequest.getStatusType()); // StatusType alanını güncelle
          return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
    return userRepository.findActiveUserById(userId)
        .map(user -> {
          userRepository.softDeleteUser(userId);
          return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
  }

  // Geçerli provider değerlerini kontrol eden yardımcı bir yöntem (opsiyonel)
  private boolean isValidProvider(String provider) {
    return provider.equalsIgnoreCase("Google") ||
           provider.equalsIgnoreCase("Email/Password") ||
           provider.equalsIgnoreCase("Microsoft");
  }
}
