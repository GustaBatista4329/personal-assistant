package com.gustavo.personalassistant.user.dto;

import com.gustavo.personalassistant.user.model.User;

import java.time.LocalDate;
import java.util.UUID;

public record UserSummaryDto(
        UUID userId,
        String name,
        String email,
        LocalDate birthdate,
        String phoneNumber
) {

    public UserSummaryDto(User user){
        this(user.getId(), user.getName(), user.getEmail(), user.getBirthdate(), user.getPhoneNumber());
    }

}
