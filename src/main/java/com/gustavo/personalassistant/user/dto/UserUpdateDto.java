package com.gustavo.personalassistant.user.dto;

import java.time.LocalDate;

public record UserUpdateDto(
        String name,
        LocalDate birthdate,
        String phoneNumber
) {
}
