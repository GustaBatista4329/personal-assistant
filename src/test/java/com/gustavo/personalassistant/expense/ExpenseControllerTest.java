package com.gustavo.personalassistant.expense;

import com.gustavo.personalassistant.controller.ExpenseController;
import com.gustavo.personalassistant.dto.expenseDto.ExpenseRecordDto;
import com.gustavo.personalassistant.dto.expenseDto.ExpenseResponseDto;
import com.gustavo.personalassistant.model.transactions.expense.Expense;
import com.gustavo.personalassistant.model.transactions.expense.ExpenseCategories;
import com.gustavo.personalassistant.model.transactions.expense.PaymentMethods;
import com.gustavo.personalassistant.model.user.User;
import com.gustavo.personalassistant.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ExpenseController.class)
@DisplayName("Testing the web layer of the ProductController")
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ExpenseService service;

    private User               user;
    private Expense            delivery;
    private ExpenseResponseDto deliveryDto;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("rafael");
        user.setEmail("rafael@hotmail.com");
        user.setPassword("12345");

        delivery = new Expense();

        delivery.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        delivery.setName("Delivery iFood - Hambúrguer");
        delivery.setMoney(new BigDecimal("-55.90"));
        delivery.setTransactionDate(LocalDate.from(LocalDateTime.now()));
        delivery.setCategory(ExpenseCategories.FOOD);
        delivery.setPaymentMethod(PaymentMethods.PIX);
        delivery.setUser(user);

        deliveryDto = new ExpenseResponseDto(delivery);
    }

    @Test
    @DisplayName("GET /api/expense/{expenseId} it should return 200 and the expense's JSON.")
    void shouldReturn200WithExpenseWhenIdExists() throws Exception {
        //ARRANGE
        when(service.findExpense(delivery.getId())).thenReturn(deliveryDto);

        //ACT + ASSERT
        mockMvc.perform(get("/api/expense/{expenseId}", delivery.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.expenseId").value(delivery.getId().toString()))
                .andExpect(jsonPath("$.name").value(delivery.getName()))
                .andExpect(jsonPath("$.money").value(delivery.getMoney().doubleValue()))
                .andExpect(jsonPath("$.transactionDate").value(delivery.getTransactionDate().toString()))
                .andExpect(jsonPath("$.paymentMethod").value(delivery.getPaymentMethod().name()))
                .andExpect(jsonPath("$.expenseCategory").value(delivery.getCategory().name()));

    }


}
