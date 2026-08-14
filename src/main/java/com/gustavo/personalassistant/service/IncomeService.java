package com.gustavo.personalassistant.service;

import com.gustavo.personalassistant.dto.IncomeDto.IncomeRegisterDto;
import com.gustavo.personalassistant.model.transactions.income.Income;
import com.gustavo.personalassistant.model.user.User;
import com.gustavo.personalassistant.repository.IncomeRepository;
import com.gustavo.personalassistant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class IncomeService {
    final private IncomeRepository incomeRepository;
    final private UserRepository userRepository;

    public Income registerIncome(IncomeRegisterDto dto){
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        Income newIncome = new Income(dto, user);

        incomeRepository.save(newIncome);
        return newIncome;
    }
}
