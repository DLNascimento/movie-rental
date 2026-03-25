package com.diego.rental.movie.dto;

public record MovieResponseDTO(Long id,
                               String title,
                               String description,
                               String genre,
                               Integer releaseYear,
                               Boolean available) {
}
