package com.gustavo.personalassistant.dto.expenseDto;

import com.gustavo.personalassistant.model.transactions.expense.Expense;
import com.gustavo.personalassistant.model.transactions.expense.ExpenseCategories;
import com.gustavo.personalassistant.model.transactions.expense.PaymentMethods;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseRecordResponseDto(
        UUID expenseId,
        String name,
        BigDecimal money,
        LocalDate transactionDate,
        PaymentMethods paymentMethod,
        ExpenseCategories expenseCategory
) {
    public ExpenseRecordResponseDto(Expense expense) {
        this(expense.getId(), expense.getName(), expense.getMoney(), expense.getTransactionDate(),
                expense.getPaymentMethod(), expense.getCategory());
    }
}
