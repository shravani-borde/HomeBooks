package com.example.HomeBooks.recommendation;

import com.example.HomeBooks.Model.Book;
import com.example.HomeBooks.Model.Like;
import com.example.HomeBooks.Repository.BookRepository;
import com.example.HomeBooks.Repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final LikeRepository likeRepository;

    private final BookRepository bookRepository;

    public List<Book> getRecommendations(String email) {

        List<Like> likes =
                likeRepository.findByUser_Email(email);

        // New user → recommend all books
        if (likes.isEmpty()) {
            return bookRepository.findAll();
        }

        // Genres user likes
        List<String> genres =
                likes.stream()
                        .map(like -> like.getBook().getGenre())
                        .distinct()
                        .collect(Collectors.toList());

        // Books from those genres
        List<Book> recommendations =
                bookRepository.findByGenreIn(genres);

        // Remove books already liked
        List<Long> likedIds =
                likes.stream()
                        .map(like -> like.getBook().getId())
                        .toList();

        recommendations.removeIf(
                book -> likedIds.contains(book.getId())
        );

        return recommendations;
    }
}