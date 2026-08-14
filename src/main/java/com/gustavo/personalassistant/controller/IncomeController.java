package com.gustavo.personalassistant.controller;

import com.gustavo.personalassistant.dto.IncomeDto.RegisterIncomeDto;
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
    public ResponseEntity<RegisterIncomeDto> registerIncome(
            @RequestBody @Valid RegisterIncomeDto registerIncomeDto,
            @RequestHeader(name = "idempotency-key", required = false) String idempotencyKey,
            UriComponentsBuilder uriBuilder
            ){

        RegisterIncomeDto registerIncome = incomeService.registerIncome(registerIncomeDto);

        URI location = uriBuilder.path("/api/income/{uuid}")
                .buildAndExpand(registerIncome.userId())
                .toUri();

        return ResponseEntity.created(location).body(registerIncome);
    }
}
