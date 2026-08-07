package com.example.HomeBooks.Controller;

import com.example.HomeBooks.Model.Book;
import com.example.HomeBooks.Service.RecommendationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping
    public ResponseEntity<List<Book>> getRecommendations() {

        return ResponseEntity.ok(
                recommendationService.getRecommendations()
        );
    }
}