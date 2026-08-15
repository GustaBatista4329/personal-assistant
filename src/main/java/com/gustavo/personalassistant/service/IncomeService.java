package com.gustavo.personalassistant.service;

import com.gustavo.personalassistant.dto.IncomeDto.IncomeRegisterDto;
import com.gustavo.personalassistant.dto.IncomeDto.IncomeResponseDto;
import com.gustavo.personalassistant.exception.NotFoundException;
import com.gustavo.personalassistant.model.transactions.income.Income;
import com.gustavo.personalassistant.model.user.User;
import com.gustavo.personalassistant.repository.IncomeRepository;
import com.gustavo.personalassistant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class IncomeService {
    final private IncomeRepository incomeRepository;
    final private UserRepository userRepository;

    public Income registerIncome(IncomeRegisterDto dto){
        User user = userRepository.findById(dto.userId())
                .orElseThrow(NotFoundException::userNotFound);

        Income newIncome = new Income(dto, user);

        incomeRepository.save(newIncome);
        return newIncome;
    }

    @Transactional(readOnly = true)
    public List<IncomeResponseDto> listAllIncomes(UUID userId){
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::userNotFound);

        return incomeRepository.findByUserId(userId)
                .stream()
                .map(IncomeResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public IncomeResponseDto findIncome(UUID incomeId){
        Income income = incomeRepository.findById(incomeId)
                .orElseThrow(NotFoundException::incomeNotFound);

        return new IncomeResponseDto(income);
    }

    @Transactional
    public void deleteIncome(UUID incomeId){
        Income income = incomeRepository.findById(incomeId)
                .orElseThrow(NotFoundException::incomeNotFound);

        incomeRepository.delete(income);
    }
}
