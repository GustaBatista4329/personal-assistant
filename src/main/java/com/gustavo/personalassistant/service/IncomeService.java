package com.gustavo.personalassistant.service;

import com.gustavo.personalassistant.dto.IncomeDto.IncomeRegisterDto;
import com.gustavo.personalassistant.dto.IncomeDto.IncomeResponseDto;
import com.gustavo.personalassistant.dto.IncomeDto.IncomeUpdateDto;
import com.gustavo.personalassistant.exception.NotFoundException;
import com.gustavo.personalassistant.model.transactions.income.Income;
import com.gustavo.personalassistant.model.transactions.income.IncomeCategories;
import com.gustavo.personalassistant.model.user.User;
import com.gustavo.personalassistant.repository.income.IncomeRepository;
import com.gustavo.personalassistant.repository.UserRepository;
import com.gustavo.personalassistant.repository.income.IncomeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class IncomeService {
    final private IncomeRepository incomeRepository;
    final private UserRepository   userRepository;

    public Income registerIncome(IncomeRegisterDto dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(NotFoundException::userNotFound);

        Income newIncome = new Income(dto, user);

        incomeRepository.save(newIncome);
        return newIncome;
    }

    @Transactional(readOnly = true)
    public List<IncomeResponseDto> listDynamicIncomes(
            UUID userId, IncomeCategories category,
            String month, Integer year, Integer day
    ) {

        Integer monthNumber = null;

        if (month != null && !month.trim().isEmpty()) {
            monthNumber = java.time.Month.valueOf(month.toUpperCase()).getValue();
        }

        Specification<Income> spec = IncomeSpecification.filterBy(
                userId, category, monthNumber, year, day);

        List<Income> incomes = incomeRepository.findAll(spec);

        return incomes.stream()
                .map(IncomeResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public IncomeResponseDto findIncomeById(UUID incomeId) {
        Income income = incomeRepository.findById(incomeId)
                .orElseThrow(NotFoundException::incomeNotFound);

        return new IncomeResponseDto(income);
    }


    @Transactional
    public IncomeResponseDto updateIncome(UUID incomeId, IncomeUpdateDto updateDto) {
        Income income = incomeRepository.findById(incomeId)
                .orElseThrow(NotFoundException::incomeNotFound);

        income.incomeUpdate(updateDto);

        return new IncomeResponseDto(income);
    }

    @Transactional
    public void deleteIncome(UUID incomeId) {
        Income income = incomeRepository.findById(incomeId)
                .orElseThrow(NotFoundException::incomeNotFound);

        incomeRepository.delete(income);
    }
}
