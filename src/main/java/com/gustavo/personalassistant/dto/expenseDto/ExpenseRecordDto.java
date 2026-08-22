package com.gustavo.personalassistant.dto.expenseDto;

import com.gustavo.personalassistant.model.transactions.expense.ExpenseCategories;
import com.gustavo.personalassistant.model.transactions.expense.PaymentMethods;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseRecordDto(
        @NotBlank(message = "Name field cannot be null") String name,
        @NotNull(message = "Money field cannot be null") @Negative(message = "Value must be negative") BigDecimal money,
        @NotNull(message = "Transaction field field cannot be null") LocalDate transactionDate,
        @NotNull(message = "Payment method field cannot be null") PaymentMethods paymentMethod,
        @NotNull(message = "Expense category field cannot be null") ExpenseCategories expenseCategory
        ) {
}
