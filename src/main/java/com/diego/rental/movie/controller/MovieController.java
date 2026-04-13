package com.diego.rental.movie.controller;

import com.diego.rental.movie.dto.CreateMovieDTO;
import com.diego.rental.movie.dto.MovieResponseDTO;
import com.diego.rental.movie.dto.UpdateMovieDTO;
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
    @PostMapping("/import")
    public ResponseEntity<MovieResponseDTO> importMovie(@RequestParam String title){
        return ResponseEntity.ok(movieService.importMovie(title));
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

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> updateById(@PathVariable Long id,
                                                       @RequestBody UpdateMovieDTO dto){
        return ResponseEntity.ok(movieService.updateById(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
