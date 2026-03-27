package com.diego.rental.rental.controller;

import com.diego.rental.movie.dto.CreateMovieDTO;
import com.diego.rental.rental.dto.CreateRentalDTO;
import com.diego.rental.rental.dto.RentalResponseDTO;
import com.diego.rental.rental.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public ResponseEntity<RentalResponseDTO> rent(@RequestBody CreateRentalDTO dto){

        RentalResponseDTO response = rentalService.rentMovie(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<RentalResponseDTO> returnMovie(@PathVariable Long id){

        RentalResponseDTO response = rentalService.returnMovie(id);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/active")
    public ResponseEntity<List<RentalResponseDTO>> findActiveRentals(){
        return ResponseEntity.ok(rentalService.findActiveRentals());
    }
}
