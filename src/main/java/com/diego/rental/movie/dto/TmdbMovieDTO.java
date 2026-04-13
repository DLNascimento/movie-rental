package com.diego.rental.movie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TmdbMovieDTO(String title,
                           String overview,
                           @JsonProperty("release_date")
                           String releaseDate) {
}
