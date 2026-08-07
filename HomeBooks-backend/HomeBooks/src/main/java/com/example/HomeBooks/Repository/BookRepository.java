package com.example.HomeBooks.Repository;

import com.example.HomeBooks.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Search by title
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Search by author
    List<Book> findByAuthorContainingIgnoreCase(String author);

    // Search by genre
    List<Book> findByGenreIgnoreCase(String genre);

    // NEW
    List<Book> findByGenreIn(List<String> genres);

    @Query("""
SELECT DISTINCT b.genre
FROM Book b
ORDER BY b.genre
""")
    List<String> getAllGenres();
}