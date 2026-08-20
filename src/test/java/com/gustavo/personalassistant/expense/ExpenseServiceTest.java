package com.gustavo.personalassistant.expense;

import com.gustavo.personalassistant.dto.expenseDto.ExpenseRecordDto;
import com.gustavo.personalassistant.model.transactions.expense.Expense;
import com.gustavo.personalassistant.model.transactions.expense.ExpenseCategories;
import com.gustavo.personalassistant.model.transactions.expense.PaymentMethods;
import com.gustavo.personalassistant.model.user.User;
import com.gustavo.personalassistant.repository.UserRepository;
import com.gustavo.personalassistant.repository.expense.ExpenseRepository;
import com.gustavo.personalassistant.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService unit tests")
public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository repository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ExpenseService service;

    private User             user;
    private Expense          deliveryExpense;
    private ExpenseRecordDto deliveryExpenseDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("rafael");
        user.setEmail("rafael@hotmail.com");
        user.setPassword("12345");

        deliveryExpense = new Expense();

        deliveryExpense.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        deliveryExpense.setName("Delivery iFood - Hambúrguer");
        deliveryExpense.setMoney(new BigDecimal("-55.90"));
        deliveryExpense.setTransactionDate(LocalDate.from(LocalDateTime.now()));
        deliveryExpense.setCategory(ExpenseCategories.FOOD);
        deliveryExpense.setPaymentMethod(PaymentMethods.PIX);
        deliveryExpense.setUser(user);

        deliveryExpenseDto = new ExpenseRecordDto(
                deliveryExpense.getName(),
                deliveryExpense.getMoney(),
                deliveryExpense.getTransactionDate(),
                deliveryExpense.getPaymentMethod(),
                deliveryExpense.getCategory(),
                user.getId());

    }

    @Test
    void shouldSaveExpenses() {
        //ARRANGE
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(repository.save(any(Expense.class))).thenReturn(deliveryExpense);

        //ACT
        Expense result = service.recordExpense(deliveryExpenseDto);

        //ASSERT
        assertNotNull(result);
        assertEquals(deliveryExpense.getId(), result.getId());
        assertEquals("Delivery iFood - Hambúrguer", result.getName());
        assertEquals(new BigDecimal("-55.90"), result.getMoney());

        verify(repository, times(1)).save(any(Expense.class));

    }
}
