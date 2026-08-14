package com.gustavo.personalassistant.repository;

import com.gustavo.personalassistant.model.transactions.expense.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    List<Expense> findByUserId(UUID userId);

}
