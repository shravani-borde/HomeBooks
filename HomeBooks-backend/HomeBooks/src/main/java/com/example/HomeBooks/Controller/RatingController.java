package com.example.HomeBooks.Controller;

import com.example.HomeBooks.Service.RatingService;

import com.example.HomeBooks.dto.RatingRequest;

import com.example.HomeBooks.dto.TopRatedBookDTO;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")

public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/{bookId}")
    public ResponseEntity<String> rateBook( @PathVariable Long bookId, @Valid @RequestBody RatingRequest request) {

        ratingService.rateBook(bookId, request);

        return ResponseEntity.ok("Book rated successfully");
    }

    @GetMapping("/book/{bookId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long bookId) {
        Double average = ratingService.getAverageRating(bookId);

        return ResponseEntity.ok(average);
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<TopRatedBookDTO>> getTopRatedBooks() {
        List<TopRatedBookDTO> books = ratingService.getTopRatedBooks();

        return ResponseEntity.ok(books);
    }
}