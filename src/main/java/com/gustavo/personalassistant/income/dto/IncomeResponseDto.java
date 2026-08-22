package com.gustavo.personalassistant.income.dto;

import com.gustavo.personalassistant.income.model.Income;
import com.gustavo.personalassistant.income.model.IncomeCategories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record IncomeResponseDto(
        UUID incomeId,
        String name,
        BigDecimal money,
        LocalDate transactionDate,
        IncomeCategories category
) {

    public IncomeResponseDto(Income income){
        this(income.getId(), income.getName(), income.getMoney(), income.getTransactionDate(), income.getCategory());
    }

}
