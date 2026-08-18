package com.gustavo.personalassistant.repository.expense;

import com.gustavo.personalassistant.model.transactions.expense.Expense;
import com.gustavo.personalassistant.model.transactions.expense.ExpenseCategories;
import com.gustavo.personalassistant.model.transactions.expense.PaymentMethods;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExpenseSpecification {

    public static Specification<Expense> filterBy(
            UUID userId,
            ExpenseCategories category,
            PaymentMethods paymentMethod,
            Integer month,
            Integer year,
            Integer day
    ) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));

            if(category != null){
                predicates.add(criteriaBuilder.equal(root.get("category"), category));
            }

            if(paymentMethod != null){
                predicates.add(criteriaBuilder.equal(root.get("paymentMethod"), paymentMethod));
            }

            if (year != null) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.function(
                                "date_part",
                                Integer.class,
                                criteriaBuilder.literal("year"),
                                root.get("transactionDate")), year));
            }

            if (month != null) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.function(
                                "date_part",
                                Integer.class,
                                criteriaBuilder.literal("month"),
                                root.get("transactionDate")), month));
            }

            if (day != null) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.function(
                                "date_part",
                                Integer.class,
                                criteriaBuilder.literal("month"),
                                root.get("transactionDate")), day));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

}
