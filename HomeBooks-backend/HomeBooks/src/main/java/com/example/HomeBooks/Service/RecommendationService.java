package com.example.HomeBooks.Service;

import com.example.HomeBooks.Model.Book;
import com.example.HomeBooks.Model.Like;
import com.example.HomeBooks.Repository.BookRepository;
import com.example.HomeBooks.Repository.LikeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getRecommendations() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        List<Like> likes =
                likeRepository.findByUser_Email(email);

        if (likes.isEmpty()) {
            return bookRepository.findAll();
        }

        List<String> genres =
                likes.stream()
                        .map(like -> like.getBook().getGenre())
                        .distinct()
                        .collect(Collectors.toList());

        List<Book> recommendations =
                bookRepository.findByGenreIn(genres);

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