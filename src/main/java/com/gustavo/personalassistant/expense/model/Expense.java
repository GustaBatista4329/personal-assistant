package com.gustavo.personalassistant.expense.model;

import com.gustavo.personalassistant.expense.dto.ExpenseRecordDto;
import com.gustavo.personalassistant.expense.dto.ExpenseUpdateDto;
import com.gustavo.personalassistant.common.model.Finance;
import com.gustavo.personalassistant.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expense extends Finance {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethods paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpenseCategories category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Expense(ExpenseRecordDto dto, User user) {
        this.setName(dto.name());
        this.setMoney(dto.money());
        this.setTransactionDate(dto.transactionDate());

        this.paymentMethod = dto.paymentMethod();
        this.category = dto.expenseCategory();
        this.user = user;
    }
    public void updateExpense(ExpenseUpdateDto dto){
        if (dto.name() != null) {
            this.setName(dto.name());
        }
        if (dto.money() != null) {
            this.setMoney(dto.money());
        }
        if (dto.transactionDate() != null) {
            this.setTransactionDate(dto.transactionDate());
        }
        if (dto.expenseCategory() != null) {
            this.category = dto.expenseCategory();
        }

        if(dto.paymentMethod() != null){
            this.paymentMethod = dto.paymentMethod();
        }
    }

}
