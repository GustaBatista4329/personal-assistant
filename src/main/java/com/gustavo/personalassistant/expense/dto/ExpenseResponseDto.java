package com.gustavo.personalassistant.expense.dto;

import com.gustavo.personalassistant.expense.model.Expense;
import com.gustavo.personalassistant.expense.model.ExpenseCategories;
import com.gustavo.personalassistant.expense.model.PaymentMethods;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseResponseDto(
        UUID expenseId,
        String name,
        BigDecimal money,
        LocalDate transactionDate,
        PaymentMethods paymentMethod,
        ExpenseCategories expenseCategory
) {
    public ExpenseResponseDto(Expense expense) {
        this(expense.getId(), expense.getName(), expense.getMoney(), expense.getTransactionDate(),
                expense.getPaymentMethod(), expense.getCategory());
    }
}
