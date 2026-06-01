package com.example.HomeBooks.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TopRatedBookDTO {
    private String title;
    private Double averageRating;
}