package com.gustavo.personalassistant.service;

import com.gustavo.personalassistant.dto.expenseDto.RecordExpenseDto;
import com.gustavo.personalassistant.model.transactions.expense.Expense;
import com.gustavo.personalassistant.model.user.User;
import com.gustavo.personalassistant.repository.ExpenseRepository;
import com.gustavo.personalassistant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    final private ExpenseRepository expenseRepository;
    final private UserRepository    userRepository;

    public RecordExpenseDto recordExpense(RecordExpenseDto dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        Expense expense = new Expense(dto, user);
        expenseRepository.save(expense);
        return dto;
    }
}
