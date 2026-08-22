package com.gustavo.personalassistant.income.controller;

import com.gustavo.personalassistant.income.dto.IncomeRegisterDto;
import com.gustavo.personalassistant.income.dto.IncomeResponseDto;
import com.gustavo.personalassistant.income.dto.IncomeUpdateDto;
import com.gustavo.personalassistant.income.model.Income;
import com.gustavo.personalassistant.income.model.IncomeCategories;
import com.gustavo.personalassistant.user.model.User;
import com.gustavo.personalassistant.income.service.IncomeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            UriComponentsBuilder uriBuilder,
            @AuthenticationPrincipal User user
    ) {

        Income            registerIncome = incomeService.registerIncome(incomeRegisterDto, user.getId());
        IncomeResponseDto incomeResponse = new IncomeResponseDto(registerIncome);

        URI location = uriBuilder.path("/api/income/{uuid}")
                .buildAndExpand(incomeResponse.incomeId())
                .toUri();

        return ResponseEntity.created(location).body(incomeResponse);
    }

    @GetMapping("/list")
    public ResponseEntity<List<IncomeResponseDto>> listIncomes(
            @RequestParam(required = false) String month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer day,
            @RequestParam(name = "category", required = false) IncomeCategories incomeCategory,
            @RequestParam(name = "name", required = false) String incomeName,
            @AuthenticationPrincipal User user) {

        List<IncomeResponseDto> incomeList = incomeService.listDynamicIncomes(user.getId(), incomeName, incomeCategory, month, year, day);

        return ResponseEntity.ok(incomeList);
    }

    @GetMapping("/{incomeId}")
    public ResponseEntity<IncomeResponseDto> findIncomeById(
            @PathVariable UUID incomeId,
            @AuthenticationPrincipal User user) {
        IncomeResponseDto incomeResponse = incomeService.findIncomeById(incomeId, user.getId());

        return ResponseEntity.ok(incomeResponse);
    }

    @PatchMapping("/{incomeId}")
    public ResponseEntity<IncomeResponseDto> updateIncome(
            @PathVariable UUID incomeId,
            @RequestBody @Valid IncomeUpdateDto dto,
            @AuthenticationPrincipal User user
    ) {

        return ResponseEntity.ok(incomeService.updateIncome(incomeId, dto, user.getId()));
    }

    @DeleteMapping("/delete/{incomeId}")
    public ResponseEntity<Void> deleteIncome(
            @PathVariable UUID incomeId,
            @AuthenticationPrincipal User user) {
        incomeService.deleteIncome(incomeId, user.getId());

        return ResponseEntity.noContent().build();
    }
}
