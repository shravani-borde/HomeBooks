package com.example.HomeBooks.recommendation;

import com.example.HomeBooks.Model.Book;
import com.example.HomeBooks.Repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    private final PythonRunner pythonRunner;
    private final BookRepository bookRepository;

    public RecommendationService(
            PythonRunner pythonRunner,
            BookRepository bookRepository
    ) {
        this.pythonRunner = pythonRunner;
        this.bookRepository = bookRepository;
    }

    public List<Book> getRecommendations(
            String email
    ) {

        List<Long> recommendedIds =
                pythonRunner.getRecommendedBookIds(
                        email
                );

        return recommendedIds.stream()
                .map(bookRepository::findById)
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .toList();
    }
}