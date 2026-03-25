package com.diego.rental.user.controller;

import com.diego.rental.user.dto.UserRequestDTO;
import com.diego.rental.user.dto.UserResponseDTO;
import com.diego.rental.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAllUsers(){

        List<UserResponseDTO> response = service.findAllUsers();

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable Long id){

        UserResponseDTO response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id,
                                                      @RequestBody UserRequestDTO requestDTO){

        UserResponseDTO response = service.updateUser(id, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}
