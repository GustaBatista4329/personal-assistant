package com.gustavo.personalassistant.expense.repository;

import com.gustavo.personalassistant.expense.model.Expense;
import com.gustavo.personalassistant.expense.model.ExpenseCategories;
import com.gustavo.personalassistant.expense.model.PaymentMethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID>, JpaSpecificationExecutor<Expense> {

    Optional<Expense> findById(UUID expenseId);

    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId " +
            "AND MONTH(e.transactionDate) = :month AND YEAR(e.transactionDate) = :year")
    List<Expense> findByMonthAndYear(
            @Param("userId") UUID userId,
            @Param("month") int month,
            @Param("year") Integer year);

    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND MONTH(e.transactionDate) = :month " +
            "AND YEAR(e.transactionDate) = :year AND DAY(e.transactionDate) = :day")
    List<Expense> findByDate(
            @Param("userId") UUID userId,
            @Param("month") int month,
            @Param("year") Integer year,
            @Param("day") int day);
}


