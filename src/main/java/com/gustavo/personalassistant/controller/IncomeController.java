package com.gustavo.personalassistant.controller;

import com.gustavo.personalassistant.dto.IncomeDto.IncomeRegisterDto;
import com.gustavo.personalassistant.dto.IncomeDto.IncomeResponseDto;
import com.gustavo.personalassistant.dto.IncomeDto.IncomeUpdateDto;
import com.gustavo.personalassistant.model.transactions.income.Income;
import com.gustavo.personalassistant.model.transactions.income.IncomeCategories;
import com.gustavo.personalassistant.service.IncomeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/income")
@RequiredArgsConstructor
public class IncomeController {
    final private IncomeService incomeService;

    @PostMapping
    public ResponseEntity<IncomeResponseDto> registerIncome(
            @RequestBody @Valid IncomeRegisterDto incomeRegisterDto,
            @RequestHeader(name = "idempotency-key", required = false) String idempotencyKey,
            UriComponentsBuilder uriBuilder
    ) {

        Income            registerIncome = incomeService.registerIncome(incomeRegisterDto);
        IncomeResponseDto incomeResponse = new IncomeResponseDto(registerIncome);

        URI location = uriBuilder.path("/api/income/{uuid}")
                .buildAndExpand(incomeResponse.incomeId())
                .toUri();

        return ResponseEntity.created(location).body(incomeResponse);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<IncomeResponseDto>> listIncomes(
            @PathVariable UUID userId,
            @RequestParam(required = false) String month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer day,
            @RequestParam(name = "category", required = false) IncomeCategories incomeCategory,
            @RequestParam(name = "name", required = false) String incomeName) {

        List<IncomeResponseDto> incomeList = incomeService.listDynamicIncomes(userId, incomeName, incomeCategory, month, year, day);

        return ResponseEntity.ok(incomeList);
    }

    @GetMapping("/{incomeId}")
    public ResponseEntity<IncomeResponseDto> findIncomeById(@PathVariable UUID incomeId) {
        IncomeResponseDto incomeResponse = incomeService.findIncomeById(incomeId);

        return ResponseEntity.ok(incomeResponse);
    }

    @PatchMapping("/{incomeId}")
    public ResponseEntity<IncomeResponseDto> updateIncome(
            @PathVariable UUID incomeId,
            @RequestBody @Valid IncomeUpdateDto dto
    ) {

        return ResponseEntity.ok(incomeService.updateIncome(incomeId, dto));
    }

    @DeleteMapping("/delete/{incomeId}")
    public ResponseEntity<Void> deleteIncome(@PathVariable UUID incomeId) {
        incomeService.deleteIncome(incomeId);

        return ResponseEntity.noContent().build();
    }
}
