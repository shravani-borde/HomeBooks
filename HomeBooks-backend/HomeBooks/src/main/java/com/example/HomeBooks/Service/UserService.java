package com.example.HomeBooks.Service;

import com.example.HomeBooks.Model.Book;
import com.example.HomeBooks.Model.User;
import com.example.HomeBooks.Repository.UserRepository;
import com.example.HomeBooks.Repository.BookRepository;
import com.example.HomeBooks.dto.BookResponseDTO;
import com.example.HomeBooks.dto.LoginRequest;
import com.example.HomeBooks.mapper.BookMapper;
import com.example.HomeBooks.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private BookRepository bookRepository;

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

    public void addToTbr(
            String email,
            Long bookId
    ) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "User not found"
                                        )
                        );

        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Book not found"
                        )
                );

        user.getTbrBooks()
                .add(book);

        userRepository.save(user);
    }

    public Set<BookResponseDTO> getTbrBooks(
            String email
    ) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "User not found"
                                        )
                        );

        return user.getTbrBooks()
                .stream()
                .map(BookMapper::toDTO)
                .collect(
                        Collectors.toSet()
                );
    }

    public void likeBook(
            String email,
            Long bookId
    ) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "User not found"
                                        )
                        );

        Book book =
                bookRepository
                        .findById(bookId)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Book not found"
                                        )
                        );

        user.getLikedBooks()
                .add(book);

        userRepository.save(user);
    }

    public Set<BookResponseDTO>
    getLikedBooks(
            String email
    ) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "User not found"
                                        )
                        );

        return user.getLikedBooks()
                .stream()
                .map(BookMapper::toDTO)
                .collect(
                        Collectors.toSet()
                );
    }
}