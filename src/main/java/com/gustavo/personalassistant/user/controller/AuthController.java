package com.gustavo.personalassistant.user.controller;

import com.gustavo.personalassistant.user.dto.*;
import com.gustavo.personalassistant.infra.config.JwtService;
import com.gustavo.personalassistant.user.model.User;
import com.gustavo.personalassistant.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService           userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService            jwtService;

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserSummaryDto> registerUser(
            @RequestBody @Valid UserRegistrationDto userRegistration,

            UriComponentsBuilder uriBuilder
    ) {

        UserSummaryDto newUser = userService.userRegistration(userRegistration);

        URI location = uriBuilder.path("/api/auth/users/{uuid}")
                .buildAndExpand(newUser.userId())
                .toUri();

        return ResponseEntity.created(location).body(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequest) {
        var credential     = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        var authentication = authenticationManager.authenticate(credential);
        var user           = (User) authentication.getPrincipal();

        return ResponseEntity.ok(new LoginResponseDto(
                jwtService.generateToken(user),
                "Bearer",
                new UserSummaryDto(user)
        ));
    }

    @GetMapping("/me")
    public ResponseEntity<UserSummaryDto> me(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(new UserSummaryDto(user));
    }

    @PatchMapping("/update")
    public ResponseEntity<UserSummaryDto> updateUser(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UserUpdateDto dto) {

        return ResponseEntity.ok(userService.updateUser(user.getId(), dto));
    }

}
