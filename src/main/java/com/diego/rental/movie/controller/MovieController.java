package com.diego.rental.movie.controller;

import com.diego.rental.movie.dto.CreateMovieDTO;
import com.diego.rental.movie.dto.MovieResponseDTO;
import com.diego.rental.movie.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieResponseDTO> create(@RequestBody CreateMovieDTO dto){

        MovieResponseDTO response = movieService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<MovieResponseDTO>> findAll(){

        List<MovieResponseDTO> response = movieService.findAll();
        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> findById(@PathVariable Long id){

        MovieResponseDTO response = movieService.findById(id);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
