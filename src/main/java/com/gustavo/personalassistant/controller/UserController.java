package com.gustavo.personalassistant.controller;

import com.gustavo.personalassistant.dto.userDto.UserRegistrationDto;
import com.gustavo.personalassistant.dto.userDto.UserResponseDto;
import com.gustavo.personalassistant.model.user.User;
import com.gustavo.personalassistant.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> registerUser(
            @RequestBody @Valid UserRegistrationDto userRegistration,
            @RequestHeader(name = "Idempotency-Key", required = false) String idempotencykey,
            UriComponentsBuilder uriBuilder
    ){

        User newUser = userService.userRegistration(userRegistration);

        UserResponseDto user = new UserResponseDto(newUser);

        URI location = uriBuilder.path("/api/user/users/{uuid}")
                .buildAndExpand(newUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(user);
    }

}
