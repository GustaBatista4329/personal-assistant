package com.gustavo.personalassistant.repository;

import com.gustavo.personalassistant.model.transactions.expense.Expense;
import com.gustavo.personalassistant.model.transactions.expense.ExpenseCategories;
import com.gustavo.personalassistant.model.transactions.expense.PaymentMethods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    List<Expense> findByUserId(UUID userId);

    Optional<Expense> findById(UUID expenseId);

    List<Expense> findByCategoryAndUserId(ExpenseCategories category, UUID userId);

    List<Expense> findByPaymentMethodAndUserId(PaymentMethods paymentMethod, UUID userId);

    List<Expense> findByPaymentMethodAndCategoryAndUserId(
            PaymentMethods paymentMethod,ExpenseCategories category, UUID userId
    );


}
