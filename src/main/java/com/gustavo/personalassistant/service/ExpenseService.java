package com.gustavo.personalassistant.service;

import com.gustavo.personalassistant.dto.expenseDto.ExpenseRecordDto;
import com.gustavo.personalassistant.dto.expenseDto.ExpenseResponseDto;
import com.gustavo.personalassistant.dto.expenseDto.ExpenseUpdateDto;
import com.gustavo.personalassistant.exception.NotFoundException;
import com.gustavo.personalassistant.model.transactions.expense.Expense;
import com.gustavo.personalassistant.model.transactions.expense.ExpenseCategories;
import com.gustavo.personalassistant.model.transactions.expense.PaymentMethods;
import com.gustavo.personalassistant.model.user.User;
import com.gustavo.personalassistant.repository.expense.ExpenseRepository;
import com.gustavo.personalassistant.repository.expense.ExpenseSpecification;
import com.gustavo.personalassistant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
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
                .orElseThrow(NotFoundException::userNotFound);

        Expense newExpense = new Expense(dto, user);
        expenseRepository.save(newExpense);
        return newExpense;
    }

    @Transactional(readOnly = true)
    public List<ExpenseResponseDto> listDynamicExpenses(
            UUID userId, String name, ExpenseCategories category, PaymentMethods paymentMethod,
            String month, Integer year, Integer day
    ) {

        Integer monthNumber = null;

        if (month != null && !month.trim().isEmpty()) {
            monthNumber = java.time.Month.valueOf(month.toUpperCase()).getValue();
        }

        Specification<Expense> spec = ExpenseSpecification.filterBy(
                userId, name, category, paymentMethod, monthNumber, year, day);

        List<Expense> expenses = expenseRepository.findAll(spec);

        return expenses.stream()
                .map(ExpenseResponseDto::new)
                .toList();
    }


    @Transactional(readOnly = true)
    public ExpenseResponseDto findExpense(UUID expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(NotFoundException::expenseNotFound);

        return new ExpenseResponseDto(expense);
    }

    @Transactional
    public ExpenseResponseDto updateExpense(UUID expenseId, ExpenseUpdateDto expenseUpdate){
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(NotFoundException::expenseNotFound);

        expense.updateExpense(expenseUpdate);

        return new ExpenseResponseDto(expense);
    }

    @Transactional
    public void deleteExpense(UUID expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(NotFoundException::expenseNotFound);

        expenseRepository.delete(expense);
    }
}
