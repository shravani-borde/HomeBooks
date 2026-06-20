package com.example.HomeBooks.Service;

import com.example.HomeBooks.Model.Book;
import com.example.HomeBooks.Model.User;

import com.example.HomeBooks.Repository.BookRepository;
import com.example.HomeBooks.Repository.UserRepository;

import com.example.HomeBooks.dto.BookResponseDTO;
import com.example.HomeBooks.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import java.util.List;
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

        if (!user.getTbrBooks().contains(book)) {
            user.getTbrBooks().add(book);
            userRepository.save(user);
        }
    }

    public List<BookResponseDTO> getMyTbrBooks() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return user.getTbrBooks()
                .stream()
                .map(BookMapper::toDTO)
                .toList();
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

//        // Remove from TBR
//        user.getTbrBooks().remove(book);
//
//        // Save changes
//        userRepository.save(user);
//
//
        System.out.println("Before remove: " +
                user.getTbrBooks().size());

        boolean removed = user.getTbrBooks()
                .removeIf(
                        b -> b.getId().equals(bookId)
                );

        userRepository.save(user);

        System.out.println("Removed: " + removed);

        System.out.println("After remove: " +
                user.getTbrBooks().size());

        userRepository.save(user);
    }
}