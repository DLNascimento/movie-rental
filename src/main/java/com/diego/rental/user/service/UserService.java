package com.diego.rental.user.service;

import com.diego.rental.shared.exception.BusinessException;
import com.diego.rental.user.dto.UserRequestDTO;
import com.diego.rental.user.dto.UserResponseDTO;
import com.diego.rental.user.entity.UserEntity;
import com.diego.rental.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserResponseDTO create(UserRequestDTO requestDTO) {

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

    public List<UserResponseDTO> findAllUsers() {

        return userRepository.findAll().stream().map(user -> new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail()
        )).toList();

    }

    public UserResponseDTO findById(Long id) {

        UserEntity user = userRepository.findById(id).orElseThrow(() -> new BusinessException("User not found"));
        return new UserResponseDTO(user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail());

    }


    public UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO) {

        UserEntity user = userRepository.findById(id).orElseThrow(() -> new BusinessException("User not found"));

        if (requestDTO.email() != null) {
            userRepository.findByEmail(requestDTO.email()).
                    ifPresent(existing -> {
                        if (!existing.getId().equals(id)) {
                            throw new BusinessException("Email already exists");
                        }
                    });
        }

        if (requestDTO.cpf() != null){
            userRepository.findByCpf(requestDTO.cpf()).
                    ifPresent(existing -> {
                        if (!existing.getId().equals(id)){
                            throw new BusinessException("CPF already exists");
                        }

            });
        }

        if (requestDTO.name() != null){
            user.setName(requestDTO.name()) ;
        }
        if (requestDTO.lastName() != null){
            user.setLastName(requestDTO.lastName());
        }
        if (requestDTO.email() != null) {
            user.setEmail(requestDTO.email());
        }

        if (requestDTO.cpf() != null) {
            user.setCpf(requestDTO.cpf());
        }

        UserEntity updated = userRepository.save(user);

        return new UserResponseDTO(
                updated.getId(),
                updated.getName(),
                updated.getLastName(),
                updated.getEmail()
        );

    }


}
