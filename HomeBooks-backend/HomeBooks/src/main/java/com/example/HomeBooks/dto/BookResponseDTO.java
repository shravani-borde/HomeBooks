package com.example.HomeBooks.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookResponseDTO {
    private Long id;

    private String title;

    private String author;

    private String genre;

    private String description;

    private Double rating;

    private String coverImage;
}
