package com.gustavo.personalassistant.model.transactions.income;

import com.gustavo.personalassistant.model.transactions.Finance;
import com.gustavo.personalassistant.model.user.User;
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

}
