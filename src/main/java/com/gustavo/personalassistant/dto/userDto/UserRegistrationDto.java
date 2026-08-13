package com.gustavo.personalassistant.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record UserRegistrationDto(
        @NotBlank(message = "Name field cannot be null") String name,
        @Past(message = "Birthdate field has to be in past") LocalDate birthdate,
        @NotBlank(message = "Phone number field cannot be null") String phonenumber,
        @NotBlank(message = "Email field cannot be null") @Email(message = "Type a valid email address") String email,
        @NotBlank(message = "Password field cannot be null") String password) {
}
