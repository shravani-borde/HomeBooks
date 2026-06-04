package com.example.HomeBooks.Repository;

import com.example.HomeBooks.Model.Book;
import com.example.HomeBooks.Model.Like;
import com.example.HomeBooks.Model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository
        extends JpaRepository<Like, Long> {

    // Check if user already liked book
    Optional<Like> findByUserAndBook(User user, Book book);

    // Get all likes of a user
    List<Like> findByUser(User user);
}