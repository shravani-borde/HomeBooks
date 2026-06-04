package com.example.HomeBooks.Controller;

import com.example.HomeBooks.Model.Book;
import com.example.HomeBooks.Service.TbrService;

import com.example.HomeBooks.dto.BookResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<List<BookResponseDTO>> getMyTbrBooks() {

        return ResponseEntity.ok(
                tbrService.getMyTbrBooks()
        );
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