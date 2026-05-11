package org.psembass.financeflow.specification;

import jakarta.persistence.criteria.JoinType;
import org.psembass.financeflow.entity.Transaction;
import org.psembass.financeflow.enums.CategoryType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TransactionSpecification {

    public static Specification<Transaction> forUser(Long userId) {
        return (root, query, cb) ->
                cb.equal(root.get("userId"), userId);
    }

    public static Specification<Transaction> hasCategory(Long categoryId) {
        return (root, query, cb) ->
                cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Transaction> fromDate(LocalDate from) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("date"), from);
    }

    public static Specification<Transaction> toDate(LocalDate to) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("date"), to);
    }

    public static Specification<Transaction> hasType(CategoryType type) {
        return (root, query, cb) ->
                cb.equal(root.get("category").get("type"), type);
    }

    public static Specification<Transaction> withCategoryFetch() {
        return (root, query, cb) -> {
            if (query.getResultType() != Long.class) {
                root.fetch("category", JoinType.LEFT);
            }
            return cb.conjunction();
        };
    }
}
