package com.gustavo.personalassistant.model.transactions.expense;

import com.gustavo.personalassistant.dto.expenseDto.RecordExpenseDto;
import com.gustavo.personalassistant.model.transactions.Finance;
import com.gustavo.personalassistant.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "expenses")
@AttributeOverride(name = "financeId", column = @Column(name = "expense_id"))
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

    public Expense(RecordExpenseDto dto, User user) {
        this.setName(dto.name());
        this.setMoney(dto.money());
        this.setTransactionDate(dto.transactionDate());

        this.paymentMethod = dto.paymentMethod();
        this.category = dto.expenseCategory();
        this.user = user;
    }
}
