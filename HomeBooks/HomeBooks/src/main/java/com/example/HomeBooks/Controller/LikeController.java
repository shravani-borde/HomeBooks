package com.example.HomeBooks.Controller;

import com.example.HomeBooks.Model.Book;
import com.example.HomeBooks.Service.LikeService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")

public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/{bookId}")
    public ResponseEntity<String> likeBook(@PathVariable Long bookId) {

        likeService.likeBook(bookId);

        return ResponseEntity.ok("Book liked successfully");
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> unlikeBook(
            @PathVariable Long bookId
    ) {

        likeService.unlikeBook(bookId);

        return ResponseEntity.ok(
                "Book unliked successfully"
        );
    }

    @GetMapping
    public ResponseEntity<List<Book>> getLikedBooks() {

        List<Book> books =
                likeService.getLikedBooks();

        return ResponseEntity.ok(books);
    }
}