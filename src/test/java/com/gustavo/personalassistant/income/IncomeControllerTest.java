package com.gustavo.personalassistant.income;

import com.gustavo.personalassistant.income.controller.IncomeController;
import com.gustavo.personalassistant.income.dto.IncomeResponseDto;
import com.gustavo.personalassistant.infra.exception.NotFoundException;
import com.gustavo.personalassistant.income.model.Income;
import com.gustavo.personalassistant.income.model.IncomeCategories;
import com.gustavo.personalassistant.user.model.User;
import com.gustavo.personalassistant.income.service.IncomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@WebMvcTest(IncomeController.class)
@DisplayName("Testing the web layer of the ProductController")
public class IncomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IncomeService service;

    private User              user;
    private Income            salary;
    private IncomeResponseDto salaryDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("rafael");
        user.setEmail("rafael@hotmail.com");
        user.setPassword("12345");

        salary = new Income();

        salary.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174001"));
        salary.setName("salary");
        salary.setMoney(new BigDecimal("1600.00"));
        salary.setTransactionDate(LocalDate.from(LocalDateTime.now()));
        salary.setCategory(IncomeCategories.SALARY);
        salary.setUser(user);

        salaryDto = new IncomeResponseDto(salary);
    }

    @Test
    @DisplayName("GET /api/income/{incomeId} it should return 200 and the income's JSON")
    void shouldReturn200WhitIncomeWhenIdExists() throws Exception {
        //ARRANGE
        when(service.findIncomeById(salary.getId())).thenReturn(salaryDto);

        //ACT + ASSERT
        mockMvc.perform(get("/api/income/{incomeId}", salary.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.incomeId").value(salary.getId().toString()))
                .andExpect(jsonPath("$.name").value(salary.getName()))
                .andExpect(jsonPath("$.money").value(salary.getMoney().doubleValue()))
                .andExpect(jsonPath("$.transactionDate").value(salary.getTransactionDate().toString()))
                .andExpect(jsonPath("$.category").value(salary.getCategory().name()));
    }

    @Test
    @DisplayName("GET /api/income/{incomeId} it must return a 404 when the income does not exist.")
    void shouldReturn404WhenIncomeNotExists() throws Exception {
        UUID incomeId = UUID.randomUUID();

        when(service.findIncomeById(incomeId))
                .thenThrow(NotFoundException.incomeNotFound());

        mockMvc.perform(get("/api/income/{incomeId}", incomeId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    @DisplayName("GET /api/income/user/{userId} It should return 200 and the filtered income json list.")
    void shouldReturn200WithFilteredIncomesWhenFilterIsPassed() throws Exception {
        List<IncomeResponseDto> expectedList = java.util.List.of(salaryDto);

        when(service.listDynamicIncomes(
                user.getId(), null, IncomeCategories.SALARY, null, null, null)
        ).thenReturn(expectedList);

        mockMvc.perform(get("/api/income/user/{userId}", salary.getUser().getId())
                        .param("category", IncomeCategories.SALARY.name())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].incomeId").value(salary.getId().toString()))
                .andExpect(jsonPath("$[0].name").value(salary.getName()))
                .andExpect(jsonPath("$[0].money").value(salary.getMoney().doubleValue()))
                .andExpect(jsonPath("$[0].category").value(salary.getCategory().name()));
    }

    @Test
    @DisplayName("DELETE /api/income/{incomeId} it should return 204 and delete the income")
    void shouldReturn204AndDeleteIncome() throws Exception {
        doNothing().when(service).deleteIncome(salary.getId());

        mockMvc.perform(delete("/api/income/delete/{incomeId}", salary.getId()))
                .andExpect(status().isNoContent());
    }

}
