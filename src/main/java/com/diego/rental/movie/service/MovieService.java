package com.diego.rental.movie.service;

import com.diego.rental.movie.client.TmdbClient;
import com.diego.rental.movie.dto.CreateMovieDTO;
import com.diego.rental.movie.dto.MovieResponseDTO;
import com.diego.rental.movie.dto.UpdateMovieDTO;
import com.diego.rental.movie.entity.MovieEntity;
import com.diego.rental.movie.mapper.MovieMapper;
import com.diego.rental.movie.repository.MovieRepository;
import com.diego.rental.shared.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository repository;
    private final TmdbClient client;
    private final MovieMapper movieMapper;

    public MovieService(MovieRepository repository, TmdbClient client, MovieMapper movieMapper) {
        this.repository = repository;
        this.client = client;
        this.movieMapper = movieMapper;
    }


    public MovieResponseDTO create(CreateMovieDTO createMovieDTO) {

        MovieEntity movie = new MovieEntity();
        movie.setTitle(createMovieDTO.title());
        movie.setDescription(createMovieDTO.description());
        movie.setGenre(createMovieDTO.genre());
        movie.setReleaseYear(createMovieDTO.releaseYear());
        MovieEntity saved = repository.save(movie);
        return toResponse(saved);
    }

    public List<MovieResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public MovieResponseDTO findById(Long id) {

        MovieEntity entity = repository.findById(id).orElseThrow(() -> new BusinessException("Movie not found"));
        return toResponse(entity);

    }

    public MovieResponseDTO updateById(Long id, UpdateMovieDTO dto) {

        MovieEntity movie = repository.findById(id).orElseThrow(() -> new BusinessException("Movie not found"));

        if (dto.title() != null) movie.setTitle(dto.title());
        if (dto.description() != null) movie.setDescription(dto.description());
        if (dto.genre() != null) movie.setGenre(dto.genre());
        if (dto.releaseYear() != null) movie.setReleaseYear(dto.releaseYear());

        MovieEntity updated = repository.save(movie);

        return toResponse(updated);

    }

    public void deleteById(Long id) {
        MovieEntity entity = repository.findById(id).orElseThrow(() -> new BusinessException("Movie not found"));
        repository.deleteById(id);
    }

    private MovieResponseDTO toResponse(MovieEntity entity) {

        return new MovieResponseDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getGenre(),
                entity.getReleaseYear(),
                entity.getStatus()
        );

    }

    public MovieResponseDTO importMovie(String title) {


        try {

            var response = client.searchMovie(title);

            System.out.println("RESPONSE: " + response);

            if (response == null || response.results().isEmpty()) {
                throw new RuntimeException("Filme não encontrado");
            }

            var tmdbMovie = response.results().get(0);

            System.out.println("TMDB MOVIE: " + tmdbMovie);

            if (repository.existsByTitleIgnoreCase(tmdbMovie.title())) {
                throw new RuntimeException("Filme já cadastrado");
            }

            var movie = movieMapper.fromTmdb(tmdbMovie);

            System.out.println("ENTITY: " + movie);

            var saved = repository.save(movie);

            return movieMapper.toResponse(saved);

        } catch (Exception e) {
            e.printStackTrace(); // 👈 ISSO AQUI É O MAIS IMPORTANTE
            throw e;
        }
    }
}
