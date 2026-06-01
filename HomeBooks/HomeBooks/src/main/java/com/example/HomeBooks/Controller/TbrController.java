package com.example.HomeBooks.Controller;

import com.example.HomeBooks.Model.Book;
import com.example.HomeBooks.Service.TbrService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/tbr")

public class TbrController {

    @Autowired
    private TbrService tbrService;

    @PostMapping("/{bookId}")
    public ResponseEntity<String> addBookToTbr(
            @PathVariable Long bookId
    ) {

        tbrService.addBookToTbr(bookId);

        return ResponseEntity.ok(
                "Book added to TBR"
        );
    }

    @GetMapping
    public ResponseEntity<Set<Book>> getMyTbrBooks() {

        Set<Book> books =
                tbrService.getMyTbrBooks();

        return ResponseEntity.ok(books);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> removeBookFromTbr(
            @PathVariable Long bookId
    ) {

        tbrService.removeBookFromTbr(bookId);

        return ResponseEntity.ok(
                "Book removed from TBR"
        );
    }
}