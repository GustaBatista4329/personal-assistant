package com.gustavo.personalassistant.income.model;

import com.gustavo.personalassistant.income.dto.IncomeRegisterDto;
import com.gustavo.personalassistant.income.dto.IncomeUpdateDto;
import com.gustavo.personalassistant.common.model.Finance;
import com.gustavo.personalassistant.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "incomes")
@AttributeOverride(name = "financeId", column = @Column(name = "income_id"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Income extends Finance {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncomeCategories category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Income(IncomeRegisterDto dto, User user){
        this.setName(dto.name());
        this.setMoney(dto.money());
        this.setTransactionDate(dto.transactionDate());

        this.category = dto.category();

        this.user = user;
    }


    public void incomeUpdate(IncomeUpdateDto dto){
        if (dto.name() != null) {
            this.setName(dto.name());
        }
        if (dto.money() != null) {
            this.setMoney(dto.money());
        }
        if (dto.transactionDate() != null) {
            this.setTransactionDate(dto.transactionDate());
        }
        if (dto.category() != null) {
            this.category = dto.category();
        }
    }
}
