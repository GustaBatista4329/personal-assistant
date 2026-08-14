package com.gustavo.personalassistant.repository;

import com.gustavo.personalassistant.model.transactions.income.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IncomeRepository extends JpaRepository<Income, UUID> {
}
