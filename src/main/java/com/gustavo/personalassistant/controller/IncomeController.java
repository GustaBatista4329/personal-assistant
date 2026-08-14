package com.gustavo.personalassistant.controller;

import com.gustavo.personalassistant.dto.IncomeDto.IncomeRegisterDto;
import com.gustavo.personalassistant.dto.IncomeDto.IncomeResponseDto;
import com.gustavo.personalassistant.model.transactions.income.Income;
import com.gustavo.personalassistant.service.IncomeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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
            ){

        Income registerIncome = incomeService.registerIncome(incomeRegisterDto);
        IncomeResponseDto incomeResponse = new IncomeResponseDto(registerIncome);

        URI location = uriBuilder.path("/api/income/{uuid}")
                .buildAndExpand(incomeResponse.incomeId())
                .toUri();

        return ResponseEntity.created(location).body(incomeResponse);
    }
}
