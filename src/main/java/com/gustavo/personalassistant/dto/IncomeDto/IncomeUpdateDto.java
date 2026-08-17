package com.gustavo.personalassistant.dto.IncomeDto;

import com.gustavo.personalassistant.model.transactions.income.IncomeCategories;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeUpdateDto (
        @Size(min =2, max = 100) String name,
        @Positive(message = "Value must be positive") BigDecimal money,
        LocalDate transactionDate,
        IncomeCategories category
){

}
