package com.diego.rental.user.controller;

import com.diego.rental.user.dto.UserRequestDTO;
import com.diego.rental.user.dto.UserResponseDTO;
import com.diego.rental.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO request){
        UserResponseDTO response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

}
