package com.gustavo.personalassistant.income.repository;

import com.gustavo.personalassistant.income.model.Income;
import com.gustavo.personalassistant.income.model.IncomeCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IncomeRepository extends JpaRepository<Income, UUID>, JpaSpecificationExecutor<Income> {

    Optional<Income> findById(UUID incomeId);

    @Query("SELECT e FROM Income e WHERE e.user.id = :userId " +
            "AND MONTH(e.transactionDate) = :month AND YEAR(e.transactionDate) = :year")
    List<Income> findByMonthAndYear(
            @Param("userId") UUID userId,
            @Param("month") int month,
            @Param("year") Integer year);

    @Query("SELECT e FROM Income e WHERE e.user.id = :userId AND MONTH(e.transactionDate) = :month " +
            "AND YEAR(e.transactionDate) = :year AND DAY(e.transactionDate) = :day")
    List<Income> findByDate(
            @Param("userId") UUID userId,
            @Param("month") int month,
            @Param("year") Integer year,
            @Param("day") int day);
}
