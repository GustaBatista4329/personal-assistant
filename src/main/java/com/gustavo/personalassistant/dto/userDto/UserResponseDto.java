package com.gustavo.personalassistant.dto.userDto;

import com.gustavo.personalassistant.model.user.User;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponseDto(
        UUID userId,
        String name,
        String email,
        LocalDate birthdate,
        String phoneNumber
) {

    public UserResponseDto(User user){
        this(user.getId(), user.getName(), user.getEmail(), user.getBirthdate(), user.getPhoneNumber());
    }

}
