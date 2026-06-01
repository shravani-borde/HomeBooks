package com.example.HomeBooks.Service;

import com.example.HomeBooks.Model.Book;
import com.example.HomeBooks.Model.User;

import com.example.HomeBooks.Repository.BookRepository;
import com.example.HomeBooks.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TbrService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public void addBookToTbr(Long bookId) {

        // Get current logged-in user email
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

        // Add book to TBR
        user.getTbrBooks().add(book);

        // Save user
        userRepository.save(user);
    }

    public Set<Book> getMyTbrBooks() {

        // Get current logged-in user
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

        // Return TBR books
        return user.getTbrBooks();
    }

    public void removeBookFromTbr(Long bookId) {

        // Get current logged-in user
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

        // Remove from TBR
        user.getTbrBooks().remove(book);

        // Save changes
        userRepository.save(user);
    }
}