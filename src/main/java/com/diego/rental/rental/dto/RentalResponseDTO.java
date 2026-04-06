package com.diego.rental.rental.dto;

import java.time.LocalDateTime;

public record RentalResponseDTO(
        Long id,
        Long userId,
        Long movieId,
        LocalDateTime rentalDate,
        LocalDateTime dueDate,
        LocalDateTime returnDate,
        Long fine
) {
}
