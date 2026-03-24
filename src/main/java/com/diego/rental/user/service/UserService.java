package com.diego.rental.user.service;

import com.diego.rental.shared.exception.BusinessException;
import com.diego.rental.user.dto.UserRequestDTO;
import com.diego.rental.user.dto.UserResponseDTO;
import com.diego.rental.user.entity.UserEntity;
import com.diego.rental.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserResponseDTO create(UserRequestDTO requestDTO){

        userRepository.findByEmail(requestDTO.email()).ifPresent(user -> {
            throw new BusinessException("Email already exists");
        });

        userRepository.findByCpf(requestDTO.cpf()).ifPresent(cpf -> {
            throw new BusinessException("Cpf already exists");
        });

        UserEntity user = new UserEntity(
                requestDTO.name(),
                requestDTO.lastName(),
                requestDTO.email(),
                requestDTO.cpf()
        );
        UserEntity saved = userRepository.save(user);

        return new UserResponseDTO(
                saved.getId(),
                saved.getName(),
                saved.getLastName(),
                saved.getEmail()
        );

    }


}
