package com.gustavo.personalassistant.expense.controller;

import com.gustavo.personalassistant.expense.dto.ExpenseResponseDto;
import com.gustavo.personalassistant.expense.dto.ExpenseRecordDto;
import com.gustavo.personalassistant.expense.dto.ExpenseUpdateDto;
import com.gustavo.personalassistant.expense.model.Expense;
import com.gustavo.personalassistant.expense.model.ExpenseCategories;
import com.gustavo.personalassistant.expense.model.PaymentMethods;
import com.gustavo.personalassistant.user.model.User;
import com.gustavo.personalassistant.expense.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            UriComponentsBuilder uriBuilder,
            @AuthenticationPrincipal User user
    ) {

        Expense recordExpense = expenseService.recordExpense(expenseRecordDto, user.getId());

        ExpenseResponseDto expenseResponse = new ExpenseResponseDto(recordExpense);

        URI location = uriBuilder.path("/api/expense/{uuid}")
                .buildAndExpand(expenseResponse.expenseId())
                .toUri();

        return ResponseEntity.created(location).body(expenseResponse);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ExpenseResponseDto>> listExpenses(
            @RequestParam(name = "name", required = false) String expenseName,
            @RequestParam(name = "category", required = false) ExpenseCategories expenseCategory,
            @RequestParam(name = "payment-method", required = false) PaymentMethods paymentMethod,
            @RequestParam(required = false) String month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer day,
            @AuthenticationPrincipal User user
    ) {

        List<ExpenseResponseDto> expenseList = expenseService.listDynamicExpenses(
                user.getId(), expenseName, expenseCategory, paymentMethod, month, year, day);

        return ResponseEntity.ok().body(expenseList);

    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponseDto> findExpense(
            @PathVariable UUID expenseId,
            @AuthenticationPrincipal User user) {
        ExpenseResponseDto expenseDetails = expenseService.findExpense(expenseId, user.getId());

        return ResponseEntity.ok(expenseDetails);
    }

    @PatchMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponseDto> updateExpense(
            @PathVariable UUID expenseId,
            @RequestBody @Valid ExpenseUpdateDto dto,
            @AuthenticationPrincipal User user
            ){

        return ResponseEntity.ok(expenseService.updateExpense(expenseId, dto, user.getId()));

    }

    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<Void> deleteExpense(
            @PathVariable UUID expenseId,
            @AuthenticationPrincipal User user) {
        expenseService.deleteExpense(expenseId, user.getId());

        return ResponseEntity.noContent().build();
    }

}
