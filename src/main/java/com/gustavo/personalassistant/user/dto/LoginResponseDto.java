package com.gustavo.personalassistant.user.dto;

public record LoginResponseDto(String token, String type, UserSummaryDto userSummary) {}

