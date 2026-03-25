package com.diego.rental.movie.dto;

public record MovieRequestDTO(String title,
                              String description,
                              String genre,
                              Integer releaseYear) {
}
