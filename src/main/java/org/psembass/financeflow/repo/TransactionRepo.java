package org.psembass.financeflow.repo;

import org.psembass.financeflow.dto.CategorySummary;
import org.psembass.financeflow.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    @Query("""
    SELECT new org.psembass.financeflow.dto.CategorySummary(
        t.category.id,
        t.category.name,
        t.category.type,
        SUM(t.amount)
    )
    FROM Transaction t
    WHERE t.userId = :userId
    AND t.date >= :from
    AND t.date <= :to
    GROUP BY t.category.id, t.category.name, t.category.type
    """)
    List<CategorySummary> findCategorySummary(
            @Param("userId") Long userId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to);
}
