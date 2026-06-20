package com.example.HomeBooks.Controller;

import com.example.HomeBooks.Model.User;
import com.example.HomeBooks.Repository.UserRepository;
import com.example.HomeBooks.Service.LikeService;
import com.example.HomeBooks.Service.TbrService;
import com.example.HomeBooks.Service.UserService;
import com.example.HomeBooks.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserBookController {

    @Autowired
    private UserService userService;

    @Autowired
    private TbrService tbrService;

    @Autowired
    private LikeService likeService;

    @PostMapping(
            "/tbr/{bookId}"
    )
    public ResponseEntity<String>
    addToTbr(
            @PathVariable
            Long bookId,
            Authentication auth
    ) {

        userService.addToTbr(
                auth.getName(),
                bookId
        );

        return ResponseEntity.ok(
                "Added to TBR"
        );
    }

    @GetMapping("/tbr")
    public ResponseEntity<?> getTbr(
            Authentication auth
    ) {

        return ResponseEntity.ok(
                userService
                        .getTbrBooks(
                                auth.getName()
                        )
        );
    }

    @PostMapping(
            "/liked/{bookId}"
    )
    public ResponseEntity<String>
    likeBook(
            @PathVariable
            Long bookId,
            Authentication auth
    ) {

        userService.likeBook(
                auth.getName(),
                bookId
        );

        return ResponseEntity.ok(
                "Book liked"
        );
    }

    @GetMapping("/liked")
    public ResponseEntity<?> getLiked(
            Authentication auth
    ) {

        return ResponseEntity.ok(
                userService
                        .getLikedBooks(
                                auth.getName()
                        )
        );
    }

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );

        UserResponseDTO dto =
                new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole()
                );

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/tbr/{bookId}")
    public ResponseEntity<String>
    removeFromTbr(
            @PathVariable Long bookId,
            Authentication auth
    ) {

        tbrService.removeBookFromTbr(
                bookId
        );

        return ResponseEntity.ok(
                "Removed from TBR"
        );
    }

    @DeleteMapping("/liked/{bookId}")
    public ResponseEntity<String>
    unlikeBook(
            @PathVariable Long bookId,
            Authentication auth
    ) {

        likeService.unlikeBook(
                bookId
        );

        return ResponseEntity.ok(
                "Book unliked"
        );
    }
}