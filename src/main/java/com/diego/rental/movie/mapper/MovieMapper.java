package com.diego.rental.movie.mapper;

import com.diego.rental.movie.dto.MovieResponseDTO;
import com.diego.rental.movie.dto.TmdbMovieDTO;
import com.diego.rental.movie.entity.MovieEntity;
import com.diego.rental.movie.entity.MovieStatus;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public MovieEntity fromTmdb(TmdbMovieDTO dto) {
        MovieEntity movie = new MovieEntity();

        movie.setTitle(dto.title());
        movie.setDescription(dto.overview());

        movie.setGenre("UNKNOWN");
        movie.setReleaseYear(extractYear(dto.releaseDate()));

        movie.setStatus(MovieStatus.AVAILABLE);

        return movie;
    }

    public MovieResponseDTO toResponse(MovieEntity movie) {
        return new MovieResponseDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getDescription(),
                movie.getGenre(),
                movie.getReleaseYear(),
                movie.getStatus()
        );
    }

    private Integer extractYear(String releaseDate) {
        if (releaseDate == null || releaseDate.isBlank()) {
            return null;
        }
        return Integer.parseInt(releaseDate.substring(0, 4));
    }
}