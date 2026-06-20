package com.example.HomeBooks.Service;

import com.example.HomeBooks.Model.Book;
import com.example.HomeBooks.Model.Rating;
import com.example.HomeBooks.Model.User;

import com.example.HomeBooks.Repository.BookRepository;
import com.example.HomeBooks.Repository.RatingRepository;
import com.example.HomeBooks.Repository.UserRepository;

import com.example.HomeBooks.dto.RatingRequest;

import com.example.HomeBooks.dto.TopRatedBookDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public void rateBook(
            Long bookId,
            RatingRequest request
    ) {

        // Get current user
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        // Find user
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        // Find book
        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() ->
                        new RuntimeException("Book not found")
                );

        // Check existing rating
        Rating rating = ratingRepository
                .findByUserAndBook(user, book)

                .orElse(new Rating());

        // Set data
        rating.setUser(user);
        rating.setBook(book);

        rating.setScore(request.getScore());

        rating.setRatedAt(LocalDateTime.now());

        // Save rating
        ratingRepository.save(rating);
    }

    public Double getAverageRating(Long bookId) {

        Double average = ratingRepository.getAverageRatingByBookId(bookId);

        // If no ratings yet
        if(average == null) {
            return 0.0;
        }

        return average;
    }

    public List<TopRatedBookDTO>
    getTopRatedBooks() {
        return ratingRepository.getTopRatedBooks();
    }

    public List<Rating>
    getMyRatings(String email) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "User not found")
                        );

        return ratingRepository
                .findByUser(user);
    }
}