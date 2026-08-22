package com.gustavo.personalassistant.income.service;

import com.gustavo.personalassistant.income.dto.IncomeRegisterDto;
import com.gustavo.personalassistant.income.dto.IncomeResponseDto;
import com.gustavo.personalassistant.income.dto.IncomeUpdateDto;
import com.gustavo.personalassistant.infra.exception.NotFoundException;
import com.gustavo.personalassistant.income.model.Income;
import com.gustavo.personalassistant.income.model.IncomeCategories;
import com.gustavo.personalassistant.user.model.User;
import com.gustavo.personalassistant.income.repository.IncomeRepository;
import com.gustavo.personalassistant.user.repository.UserRepository;
import com.gustavo.personalassistant.income.repository.IncomeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class IncomeService {
    final private IncomeRepository incomeRepository;
    final private UserRepository   userRepository;

    public Income registerIncome(IncomeRegisterDto dto, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::userNotFound);

        Income newIncome = new Income(dto, user);


        return incomeRepository.save(newIncome);
    }

    @Transactional(readOnly = true)
    public List<IncomeResponseDto> listDynamicIncomes(
            UUID userId, String name, IncomeCategories category,
            String month, Integer year, Integer day
    ) {

        Integer monthNumber = null;

        if (month != null && !month.trim().isEmpty()) {
            monthNumber = java.time.Month.valueOf(month.toUpperCase()).getValue();
        }

        Specification<Income> spec = IncomeSpecification.filterBy(
                userId, name, category, monthNumber, year, day);

        List<Income> incomes = incomeRepository.findAll(spec);

        return incomes.stream()
                .map(IncomeResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public IncomeResponseDto findIncomeById(UUID incomeId, UUID userId) {
        Income income = incomeRepository.findById(incomeId)
                .orElseThrow(NotFoundException::incomeNotFound);

        if(!income.getUser().getId().equals(userId)){
            throw new AccessDeniedException("You dont have permission to do this");
        }

        return new IncomeResponseDto(income);
    }


    @Transactional
    public IncomeResponseDto updateIncome(UUID incomeId, IncomeUpdateDto updateDto, UUID userId) {
        Income income = incomeRepository.findById(incomeId)
                .orElseThrow(NotFoundException::incomeNotFound);

        if(!income.getUser().getId().equals(userId)){
            throw new AccessDeniedException("You dont have permission to do this");
        }


        income.incomeUpdate(updateDto);

        return new IncomeResponseDto(income);
    }

    @Transactional
    public void deleteIncome(UUID incomeId, UUID userId) {
        Income income = incomeRepository.findById(incomeId)
                .orElseThrow(NotFoundException::incomeNotFound);

        if(!income.getUser().getId().equals(userId)){
            throw new AccessDeniedException("You dont have permission to do this");
        }

        incomeRepository.delete(income);
    }
}
