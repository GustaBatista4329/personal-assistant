package com.gustavo.personalassistant.controller;

import com.gustavo.personalassistant.dto.expenseDto.ExpenseResponseDto;
import com.gustavo.personalassistant.dto.expenseDto.ExpenseRecordDto;
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
            @RequestParam(name = "category",required = false)ExpenseCategories expenseCategory,
            @RequestParam(name = "payment-method", required = false)PaymentMethods paymentMethod
            ){

        if(expenseCategory != null && paymentMethod != null){
            return ResponseEntity.ok(
                    expenseService.findByPaymentMethodAndCategory(paymentMethod, expenseCategory, userId));
        }

        if(expenseCategory != null ){
            return ResponseEntity.ok(
                    expenseService.findByCategory(expenseCategory, userId));
        }

        if(paymentMethod != null){
            return ResponseEntity.ok(
                    expenseService.findByPaymentMethod(paymentMethod, userId));
        }

        List<ExpenseResponseDto> expenseList = expenseService.listExpenses(userId);

        return ResponseEntity.ok().body(expenseList);

    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponseDto> findExpense(@PathVariable UUID expenseId){
        ExpenseResponseDto expenseDetails = expenseService.findExpense(expenseId);

        return ResponseEntity.ok(expenseDetails);
    }

    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable UUID expenseId){
        expenseService.deleteExpense(expenseId);

        return ResponseEntity.noContent().build();
    }

}
