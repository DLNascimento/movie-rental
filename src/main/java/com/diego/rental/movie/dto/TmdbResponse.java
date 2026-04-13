package com.diego.rental.movie.dto;

import java.util.List;

public record TmdbResponse(
        List<TmdbMovieDTO> results
) {
}
