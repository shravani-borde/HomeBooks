package com.example.HomeBooks.Repository;

import com.example.HomeBooks.Model.Book;
import com.example.HomeBooks.Model.Rating;
import com.example.HomeBooks.Model.User;
import com.example.HomeBooks.dto.TopRatedBookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RatingRepository
        extends JpaRepository<Rating, Long> {

    // Find rating by user and book
    Optional<Rating> findByUserAndBook(User user, Book book);


    // Get all ratings of a user
    List<Rating> findByUser(User user);


    @Query("SELECT AVG(r.score) " + "FROM Rating r " + "WHERE r.book.id = :bookId")
    Double getAverageRatingByBookId(Long bookId);


    @Query("SELECT new com.example.HomeBooks.dto.TopRatedBookDTO(" +
                    "r.book.title, AVG(r.score)) " +
                    "FROM Rating r " +
                    "GROUP BY r.book.id, r.book.title " +
                    "ORDER BY AVG(r.score) DESC")
    List<TopRatedBookDTO> getTopRatedBooks();


}