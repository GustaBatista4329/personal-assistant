package com.gustavo.personalassistant.controller;

import com.gustavo.personalassistant.dto.expenseDto.ExpenseDetailsDto;
import com.gustavo.personalassistant.dto.expenseDto.ExpenseRecordResponseDto;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/expense", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ExpenseController {
    final private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseRecordResponseDto> recordExpense(
            @RequestBody @Valid ExpenseRecordDto expenseRecordDto,
            @RequestHeader(name = "Idempotency-Key", required = false) String idempotencyKey,
            UriComponentsBuilder uriBuilder
    ) {

        Expense recordExpense = expenseService.recordExpense(expenseRecordDto);

        ExpenseRecordResponseDto expenseResponse = new ExpenseRecordResponseDto(recordExpense);

        URI location = uriBuilder.path("/api/expense/{uuid}")
                .buildAndExpand(expenseResponse.expenseId())
                .toUri();

        return ResponseEntity.created(location).body(expenseResponse);
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<ExpenseDetailsDto>> listAllExpenses(@PathVariable UUID userId){

        List<ExpenseDetailsDto> expenseList = expenseService.listAllExpenses(userId);

        return ResponseEntity.ok().body(expenseList);

    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseDetailsDto> findExpense(@PathVariable UUID expenseId){
        ExpenseDetailsDto expenseDetails = expenseService.findExpense(expenseId);

        return ResponseEntity.ok(expenseDetails);
    }

    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable UUID expenseId){
        expenseService.deleteExpense(expenseId);

        return ResponseEntity.noContent().build();
    }

}
