package com.diego.rental.movie.dto;

public record UpdateMovieDTO(String title,
                             String description,
                             String genre,
                             Integer releaseYear) {
}
