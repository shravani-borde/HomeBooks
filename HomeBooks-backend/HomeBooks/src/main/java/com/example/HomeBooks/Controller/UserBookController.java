package com.example.HomeBooks.Controller;

import com.example.HomeBooks.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserBookController {

    @Autowired
    private UserService userService;

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
}