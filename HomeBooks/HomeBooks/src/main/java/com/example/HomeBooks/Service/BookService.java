package com.example.HomeBooks.Service;

import com.example.HomeBooks.Model.Book;
import com.example.HomeBooks.Repository.BookRepository;
import com.example.HomeBooks.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Book not found with id : "+id)
        );
    }

    public Book updateBook(Long id, Book updatedBook) {
        Book existingBook = getBookById(id);

        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setDescription(updatedBook.getDescription());
        existingBook.setGenre(updatedBook.getGenre());
        existingBook.setRating(updatedBook.getRating());
        existingBook.setCoverImage(updatedBook.getCoverImage());
        existingBook.setTitle(updatedBook.getTitle());

        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) {

        Book book = getBookById(id);

        bookRepository.delete(book);
    }

    // Search by title
    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    // Search by author
    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    // Search by genre
    public List<Book> searchByGenre(String genre) {
        return bookRepository.findByGenreIgnoreCase(genre);
    }

    public Page<Book> getBooksWithPagination(
            int page,
            int size,
            String sortBy
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy).descending()
        );

        return bookRepository.findAll(pageable);
    }
}