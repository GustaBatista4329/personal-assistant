package com.gustavo.personalassistant.dto.IncomeDto;

import com.gustavo.personalassistant.model.transactions.income.IncomeCategories;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record IncomeRegisterDto(
        @NotBlank(message = "Name field cannot be null") String name,
        @NotNull(message = "Money field cannot be null") @Positive(message = "Value must be positive") BigDecimal money,
        @NotNull(message = "Transaction field field cannot be null") LocalDate transactionDate,
        @NotNull(message = "Category field cannot be null") IncomeCategories category,
        @NotNull UUID userId
        ) {
}
