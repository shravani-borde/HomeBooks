package com.example.HomeBooks.recommendation;

import com.example.HomeBooks.Model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(
            RecommendationService recommendationService
    ) {
        this.recommendationService =
                recommendationService;
    }

    @GetMapping
    public ResponseEntity<List<Book>>
    getRecommendations(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                recommendationService
                        .getRecommendations(
                                authentication.getName()
                        )
        );
    }
}