package com.diego.rental.rental.repository;

import com.diego.rental.rental.entity.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Long> {

    List<RentalEntity> findByReturnDateIsNull();
}
