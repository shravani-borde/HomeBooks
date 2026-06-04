package com.example.HomeBooks.Service;

import com.example.HomeBooks.Model.User;
import com.example.HomeBooks.Repository.UserRepository;

import com.example.HomeBooks.dto.LoginRequest;
import com.example.HomeBooks.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(User user) {

        // Check if email already exists
        if(userRepository.existsByEmail(user.getEmail())) {

            throw new RuntimeException(
                    "Email already registered"
            );
        }

        // Hash password
        String hashedPassword =
                passwordEncoder.encode(user.getPassword());

        user.setPassword(hashedPassword);

        // Default role
        user.setRole("USER");

        // Save user
        return userRepository.save(user);
    }

    public String loginUser(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(
                    () -> new RuntimeException("Invalid email")
                );

        boolean passwordMatches = passwordEncoder.matches(
                                    loginRequest.getPassword(),
                                    user.getPassword()
                                );

        if(!passwordMatches) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole()
        );

        return token;
    }
}