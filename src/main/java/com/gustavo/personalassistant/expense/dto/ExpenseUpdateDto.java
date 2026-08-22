package com.gustavo.personalassistant.expense.dto;

import com.gustavo.personalassistant.expense.model.ExpenseCategories;
import com.gustavo.personalassistant.expense.model.PaymentMethods;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseUpdateDto(
        @Size(min =2, max = 100) String name,
        @Negative BigDecimal money,
        LocalDate transactionDate,
        PaymentMethods paymentMethod,
        ExpenseCategories expenseCategory
) {
}
