package com.gustavo.personalassistant.dto.expenseDto;

import com.gustavo.personalassistant.model.transactions.expense.Expense;
import com.gustavo.personalassistant.model.transactions.expense.ExpenseCategories;
import com.gustavo.personalassistant.model.transactions.expense.PaymentMethods;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseDetailsDto(
        String name,
        BigDecimal money,
        LocalDate transactionDate,
        PaymentMethods paymentMethod,
        ExpenseCategories category
) {

    public ExpenseDetailsDto(Expense expense){
        this(expense.getName(), expense.getMoney(), expense.getTransactionDate(), expense.getPaymentMethod(), expense.getCategory());
    }

}
