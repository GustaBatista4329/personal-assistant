package com.gustavo.personalassistant.dto.userDto;

import java.time.LocalDate;

public record UserUpdateDto(
        String name,
        LocalDate birthdate,
        String phoneNumber
) {
}
