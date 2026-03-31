package com.diego.rental.rental.service;

import com.diego.rental.movie.entity.MovieEntity;
import com.diego.rental.movie.entity.MovieStatus;
import com.diego.rental.movie.repository.MovieRepository;
import com.diego.rental.rental.dto.CreateRentalDTO;
import com.diego.rental.rental.dto.RentalResponseDTO;
import com.diego.rental.rental.entity.RentalEntity;
import com.diego.rental.rental.repository.RentalRepository;
import com.diego.rental.shared.exception.BusinessException;
import com.diego.rental.user.entity.UserEntity;
import com.diego.rental.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RentalService {

    private final RentalRepository repository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;


    public RentalService(RentalRepository repository, UserRepository userRepository, MovieRepository movieRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }


    public RentalResponseDTO rentMovie(CreateRentalDTO dto) {

        UserEntity user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new BusinessException("User not found"));

        MovieEntity movie = movieRepository.findById(dto.movieId())
                .orElseThrow(() -> new BusinessException("Movie not found"));

        if (movie.getStatus() != MovieStatus.AVAILABLE) {
            throw new BusinessException("Movie is not avaiable");
        }

        RentalEntity rental = new RentalEntity();
        rental.setUser(user);
        rental.setMovie(movie);

        movie.setStatus(MovieStatus.RENTED);

        RentalEntity saved = repository.save(rental);

        return toResponse(saved);

    }

    public RentalResponseDTO returnMovie(Long rentalId) {
        RentalEntity rental = repository.findById(rentalId)
                .orElseThrow(() -> new BusinessException("Rental not found"));

        if (rental.getReturnDate() != null) {
            throw new BusinessException("Movie already returned");
        }
        rental.setReturnDate(LocalDateTime.now());

        MovieEntity movie = rental.getMovie();
        movie.setStatus(MovieStatus.AVAILABLE);

        RentalEntity updated = repository.save(rental);
        return toResponse(updated);
    }

    public List<RentalResponseDTO> findActiveRentals() {
        return repository.findByReturnDateIsNull().
                stream().
                map(this::toResponse).
                toList();
    }

    public List<RentalResponseDTO> findActiveByUser(Long userId){

        return repository.findByUserIdAndReturnDateIsNull(userId)
                .stream()
                .map(this::toResponse).toList();

    }

    public RentalResponseDTO toResponse(RentalEntity rental) {

        return new RentalResponseDTO(
                rental.getId(),
                rental.getUser().getId(),
                rental.getMovie().getId(),
                rental.getRentalDate(),
                rental.getReturnDate()
        );

    }
}
