package com.gustavo.personalassistant.dto.userDto;

public record LoginResponseDto(String token, String type, UserSummaryDto userSummary) {}

