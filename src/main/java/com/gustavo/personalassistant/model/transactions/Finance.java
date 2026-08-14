package com.gustavo.personalassistant.model.transactions;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@MappedSuperclass
public abstract class Finance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID financeId;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal money;

    @Column(nullable = false)
    private LocalDate TransactionDate;


    protected Finance() {
    }

    public UUID getFinanceId() {
        return financeId;
    }

    public void setFinanceId(UUID financeId) {
        this.financeId = financeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public LocalDate getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        TransactionDate = transactionDate;
    }
}
