package com.gustavo.personalassistant.controller;

import com.gustavo.personalassistant.dto.expenseDto.ExpenseResponseDto;
import com.gustavo.personalassistant.dto.expenseDto.ExpenseRecordDto;
import com.gustavo.personalassistant.model.transactions.expense.Expense;
import com.gustavo.personalassistant.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/expense", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ExpenseController {
    final private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseResponseDto> recordExpense(
            @RequestBody @Valid ExpenseRecordDto expenseRecordDto,
            @RequestHeader(name = "Idempotency-Key", required = false) String idempotencyKey,
            UriComponentsBuilder uriBuilder
    ) {


        Expense recordExpense = expenseService.recordExpense(expenseRecordDto);

        ExpenseResponseDto expenseResponse = new ExpenseResponseDto(recordExpense);

        URI location = uriBuilder.path("/api/expense/{uuid}")
                .buildAndExpand(expenseResponse.expenseId())
                .toUri();

        return ResponseEntity.created(location).body(expenseResponse);
    }

}
