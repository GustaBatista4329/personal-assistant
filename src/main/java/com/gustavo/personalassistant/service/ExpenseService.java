package com.gustavo.personalassistant.service;

import com.gustavo.personalassistant.dto.expenseDto.ExpenseDetailsDto;
import com.gustavo.personalassistant.dto.expenseDto.ExpenseRecordDto;
import com.gustavo.personalassistant.model.transactions.expense.Expense;
import com.gustavo.personalassistant.model.user.User;
import com.gustavo.personalassistant.repository.ExpenseRepository;
import com.gustavo.personalassistant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    @Transactional(readOnly = true)
    public List<ExpenseDetailsDto> listAllExpenses(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        List<Expense> expenses = expenseRepository.findByUserId(userId);

        List<ExpenseDetailsDto> expenseList = expenses.stream()
                .map(ExpenseDetailsDto::new)
                .toList();

        return expenseList;
    }

}
