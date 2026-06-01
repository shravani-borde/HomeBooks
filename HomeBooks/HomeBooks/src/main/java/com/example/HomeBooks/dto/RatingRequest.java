package com.example.HomeBooks.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class RatingRequest {

    @NotNull(message = "Score is required")
    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 10, message = "Rating cannot exceed 10")
    private Double score;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}