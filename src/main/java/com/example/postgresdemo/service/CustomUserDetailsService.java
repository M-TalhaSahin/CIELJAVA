package com.example.postgresdemo.service;

import com.example.postgresdemo.model.User;
import com.example.postgresdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Burada hala UsernameNotFoundException kullanacağız
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Optional<User> ile dönen sonucu kontrol ediyoruz.
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        
        // UserDetails objesini geri döndürüyoruz.
        // Parola olmadığı için password alanı yerine boş bir string ("") kullanıyoruz.
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                "", // Parola olmadığı için boş string veriyoruz
                new ArrayList<>() // Kullanıcının rollerini buraya ekleyebilirsiniz
        );
    }
}