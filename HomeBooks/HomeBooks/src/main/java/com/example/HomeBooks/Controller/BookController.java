package com.example.HomeBooks.Controller;

import com.example.HomeBooks.Model.Book;
import com.example.HomeBooks.Service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin("")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book){
        Book savedBook = bookService.addBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBook(@PathVariable Long id, @Valid @RequestBody Book updatedBook){
        Book book = bookService.updateBook(id, updatedBook);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }

    // Search by title
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchByTitle(
            @RequestParam String keyword
    ) {

        List<Book> books = bookService.searchByTitle(keyword);

        return ResponseEntity.ok(books);
    }

    // Search by author
    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> searchByAuthor(
            @PathVariable String author
    ) {

        List<Book> books = bookService.searchByAuthor(author);

        return ResponseEntity.ok(books);
    }

    // Search by genre
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Book>> searchByGenre(
            @PathVariable String genre
    ) {

        List<Book> books = bookService.searchByGenre(genre);

        return ResponseEntity.ok(books);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Book>> getBooksWithPagination(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy
    ) {

        Page<Book> books = bookService.getBooksWithPagination(
                page,
                size,
                sortBy
        );

        return ResponseEntity.ok(books);
    }
}