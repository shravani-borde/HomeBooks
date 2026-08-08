package com.example.HomeBooks.recommendation;

import com.example.HomeBooks.Model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    public ResponseEntity<List<Book>> getRecommendations(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                recommendationService.getRecommendations(
                        authentication.getName()
                )
        );
    }
}