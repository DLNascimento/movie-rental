package com.diego.rental.movie.dto;

import com.diego.rental.movie.entity.MovieStatus;

public record MovieResponseDTO(Long id,
                               String title,
                               String description,
                               String genre,
                               Integer releaseYear,
                               MovieStatus status) {
}
