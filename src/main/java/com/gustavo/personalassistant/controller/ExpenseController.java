package com.gustavo.personalassistant.controller;

import com.gustavo.personalassistant.dto.expenseDto.ExpenseResponseDto;
import com.gustavo.personalassistant.dto.expenseDto.ExpenseRecordDto;
import com.gustavo.personalassistant.dto.expenseDto.ExpenseUpdateDto;
import com.gustavo.personalassistant.model.transactions.expense.Expense;
import com.gustavo.personalassistant.model.transactions.expense.ExpenseCategories;
import com.gustavo.personalassistant.model.transactions.expense.PaymentMethods;
import com.gustavo.personalassistant.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseResponseDto>> listExpenses(
            @PathVariable UUID userId,
            @RequestParam(name = "name", required = false) String expenseName,
            @RequestParam(name = "category", required = false) ExpenseCategories expenseCategory,
            @RequestParam(name = "payment-method", required = false) PaymentMethods paymentMethod,
            @RequestParam(required = false) String month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer day
    ) {

        List<ExpenseResponseDto> expenseList = expenseService.listDynamicExpenses(
                userId, expenseName, expenseCategory, paymentMethod, month, year, day);

        return ResponseEntity.ok().body(expenseList);

    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponseDto> findExpense(@PathVariable UUID expenseId) {
        ExpenseResponseDto expenseDetails = expenseService.findExpense(expenseId);

        return ResponseEntity.ok(expenseDetails);
    }

    @PatchMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponseDto> updateExpense(
            @PathVariable UUID expenseId,
            @RequestBody @Valid ExpenseUpdateDto dto
            ){

        return ResponseEntity.ok(expenseService.updateExpense(expenseId, dto));

    }

    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable UUID expenseId) {
        expenseService.deleteExpense(expenseId);

        return ResponseEntity.noContent().build();
    }

}
