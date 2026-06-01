package com.example.HomeBooks.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many likes belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Many likes belong to one book
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDateTime likedAt;

}