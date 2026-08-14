package com.gustavo.personalassistant.service;

import com.gustavo.personalassistant.dto.expenseDto.ExpenseRecordDto;
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

    public Expense recordExpense(ExpenseRecordDto dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        Expense newExpense = new Expense(dto, user);
        expenseRepository.save(newExpense);
        return newExpense;
    }

}
