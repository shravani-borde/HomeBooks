package com.example.HomeBooks.mapper;


import com.example.HomeBooks.Model.Book;
import com.example.HomeBooks.dto.BookResponseDTO;

private BookResponseDTO convertToDTO(Book book) {

    return new BookResponseDTO(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getGenre(),
            book.getDescription(),
            book.getRating(),
            book.getCoverImage()
    );
}