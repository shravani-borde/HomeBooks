package com.example.HomeBooks.Service;

import com.example.HomeBooks.Model.Book;
import com.example.HomeBooks.Repository.BookRepository;
import com.example.HomeBooks.dto.BookResponseDTO;
import com.example.HomeBooks.exception.ResourceNotFoundException;
import com.example.HomeBooks.mapper.BookMapper;

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

    // Internal helper method
    private Book getBookEntityById(Long id) {

        return bookRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Book not found with id : " + id
                        )
                );
    }

    // Get all books as DTOs
    public List<BookResponseDTO> getAllBooks() {

        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    // Get single book as DTO
    public BookResponseDTO getBookById(Long id) {

        return BookMapper.toDTO(
                getBookEntityById(id)
        );
    }

    // Update book
    public Book updateBook(Long id, Book updatedBook) {

        Book existingBook = getBookEntityById(id);

        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setDescription(updatedBook.getDescription());
        existingBook.setGenre(updatedBook.getGenre());
        existingBook.setRating(updatedBook.getRating());
        existingBook.setCoverImage(updatedBook.getCoverImage());
        existingBook.setTitle(updatedBook.getTitle());

        return bookRepository.save(existingBook);
    }

    // Delete book
    public void deleteBook(Long id) {

        Book book = getBookEntityById(id);

        bookRepository.delete(book);
    }

    // Search by title
    public List<BookResponseDTO> searchByTitle(String title) {

        return bookRepository
                .findByTitleContainingIgnoreCase(title)
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    // Search by author
    public List<BookResponseDTO> searchByAuthor(String author) {

        return bookRepository
                .findByAuthorContainingIgnoreCase(author)
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    // Search by genre
    public List<BookResponseDTO> searchByGenre(String genre) {

        return bookRepository
                .findByGenreIgnoreCase(genre)
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    // Pagination + Sorting
    public Page<BookResponseDTO> getBooksWithPagination(
            int page,
            int size,
            String sortBy
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy).descending()
        );

        Page<Book> books =
                bookRepository.findAll(pageable);

        return books.map(BookMapper::toDTO);
    }
}