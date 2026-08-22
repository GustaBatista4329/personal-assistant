package com.gustavo.personalassistant.income.dto;

import com.gustavo.personalassistant.income.model.IncomeCategories;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeRegisterDto(
        @NotBlank(message = "Name field cannot be null") @Size(min = 2, max = 100) String name,
        @NotNull(message = "Money field cannot be null") @Positive(message = "Value must be positive") BigDecimal money,
        @NotNull(message = "Transaction field field cannot be null") LocalDate transactionDate,
        @NotNull(message = "Category field cannot be null") IncomeCategories category
) {
}
