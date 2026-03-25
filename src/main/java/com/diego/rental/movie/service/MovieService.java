package com.diego.rental.movie.service;

import com.diego.rental.movie.dto.CreateMovieDTO;
import com.diego.rental.movie.dto.MovieResponseDTO;
import com.diego.rental.movie.entity.MovieEntity;
import com.diego.rental.movie.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public MovieResponseDTO create(CreateMovieDTO createMovieDTO){

        MovieEntity movie = new MovieEntity();
        movie.setTitle(createMovieDTO.title());
        movie.setDescription(createMovieDTO.description());
        movie.setGenre(createMovieDTO.genre());
        movie.setReleaseYear(createMovieDTO.releaseYear());

        MovieEntity saved = repository.save(movie);

        return new MovieResponseDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getGenre(),
                saved.getReleaseYear(),
                saved.getStatus()
        );

    }
}
