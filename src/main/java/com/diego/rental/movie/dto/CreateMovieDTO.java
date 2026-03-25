package com.diego.rental.movie.dto;

public record CreateMovieDTO(String title,
                             String description,
                             String genre,
                             Integer releaseYear) {
}
