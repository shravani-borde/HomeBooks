package com.example.HomeBooks.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many ratings belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Many ratings belong to one book
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    // User's rating score
    private Double score;

    // Timestamp
    private LocalDateTime ratedAt;
}