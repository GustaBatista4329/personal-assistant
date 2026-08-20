package com.gustavo.personalassistant.income;

import com.gustavo.personalassistant.dto.IncomeDto.IncomeRegisterDto;
import com.gustavo.personalassistant.model.transactions.income.Income;
import com.gustavo.personalassistant.model.transactions.income.IncomeCategories;
import com.gustavo.personalassistant.model.user.User;
import com.gustavo.personalassistant.repository.UserRepository;
import com.gustavo.personalassistant.repository.income.IncomeRepository;
import com.gustavo.personalassistant.service.IncomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Income Service unit test")
public class IncomeServiceTest {
    @Mock
    private IncomeRepository incomeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private IncomeService incomeService;

    private User user;
    private Income salary;
    private IncomeRegisterDto salaryIncomedto;


    @BeforeEach
    void setUp(){
        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("gustavo");
        user.setEmail("gustavo@hotmail.com");
        user.setPassword("12345");

        salary = new Income();

        salary.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174001"));
        salary.setName("salary");
        salary.setMoney(new BigDecimal("1600.00"));
        salary.setTransactionDate(LocalDate.from(LocalDateTime.now()));
        salary.setCategory(IncomeCategories.SALARY);
        salary.setUser(user);

        salaryIncomedto = new IncomeRegisterDto(
                salary.getName(),
                salary.getMoney(),
                salary.getTransactionDate(),
                salary.getCategory(),
                user.getId()
        );
    }

    @Test
    void shouldSaveIncomes(){
        //ARRANGE
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(incomeRepository.save(any(Income.class))).thenReturn(salary);

        //ACT
        Income result = incomeService.registerIncome(salaryIncomedto);

        //ASSERT
        assertNotNull(result);
        assertEquals(salary.getId(), result.getId());
        assertEquals(salary.getName(), result.getName());
        assertEquals(salary.getMoney(), result.getMoney());
        assertEquals(salary.getTransactionDate(), result.getTransactionDate());
        assertEquals(salary.getCategory(), result.getCategory());
    }
}
