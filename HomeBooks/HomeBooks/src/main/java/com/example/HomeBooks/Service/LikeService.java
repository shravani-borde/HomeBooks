package com.example.HomeBooks.Service;

import com.example.HomeBooks.Model.Book;
import com.example.HomeBooks.Model.Like;
import com.example.HomeBooks.Model.User;

import com.example.HomeBooks.Repository.BookRepository;
import com.example.HomeBooks.Repository.LikeRepository;
import com.example.HomeBooks.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public void likeBook(Long bookId) {

        // Get current authenticated user
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

        // Check already liked
        if(likeRepository
                .findByUserAndBook(user, book)
                .isPresent()) {

            throw new RuntimeException(
                    "Book already liked"
            );
        }

        // Create Like interaction
        Like like = new Like();

        like.setUser(user);
        like.setBook(book);

        like.setLikedAt(LocalDateTime.now());

        // Save interaction
        likeRepository.save(like);
    }

    public void unlikeBook(Long bookId) {

        // Get current authenticated user
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

        // Find like interaction
        Like like = likeRepository
                .findByUserAndBook(user, book)
                .orElseThrow(() ->
                        new RuntimeException("Book not liked yet")
                );

        // Delete interaction
        likeRepository.delete(like);
    }

    public List<Book> getLikedBooks() {

        // Get current authenticated user
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

        // Get all likes
        List<Like> likes =
                likeRepository.findByUser(user);

        // Extract books from likes
        return likes.stream()

                .map(Like::getBook)

                .collect(Collectors.toList());
    }
}