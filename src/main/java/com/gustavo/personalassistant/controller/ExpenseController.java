package com.gustavo.personalassistant.controller;

import com.gustavo.personalassistant.dto.expenseDto.RecordExpenseDto;
import com.gustavo.personalassistant.model.user.User;
import com.gustavo.personalassistant.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/expense", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ExpenseController {
    final private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<RecordExpenseDto> recordExpense(
            @RequestBody @Valid RecordExpenseDto recordExpenseDto,
            @RequestHeader(name = "Idempotency-Key", required = false) String idempotencyKey,
            UriComponentsBuilder uriBuilder
    ){


        RecordExpenseDto newExpense = expenseService.recordExpense(recordExpenseDto);

        URI location = uriBuilder.path("/api/expense/{name}")
                .buildAndExpand(newExpense.name())
                .toUri();

        return ResponseEntity.created(location).body(newExpense);
    }

}
