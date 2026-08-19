package com.gustavo.personalassistant.repository.income;

import com.gustavo.personalassistant.model.transactions.income.Income;
import com.gustavo.personalassistant.model.transactions.income.IncomeCategories;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IncomeSpecification {

    public static Specification<Income> filterBy(
            UUID userId,
            String name,
            IncomeCategories category,
            Integer month,
            Integer year,
            Integer day
    ) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));

            if(name != null){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                ));
            }

            if (category != null) {
                predicates.add(criteriaBuilder.equal(root.get("category"), category));
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
