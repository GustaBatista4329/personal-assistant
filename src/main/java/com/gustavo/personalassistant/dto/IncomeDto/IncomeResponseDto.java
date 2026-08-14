package com.gustavo.personalassistant.dto.IncomeDto;

import com.gustavo.personalassistant.model.transactions.income.Income;
import com.gustavo.personalassistant.model.transactions.income.IncomeCategories;

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
        this(income.getFinanceId(), income.getName(), income.getMoney(), income.getTransactionDate(), income.getCategory());
    }

}
