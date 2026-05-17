package org.psembass.financeflow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.psembass.financeflow.dto.CategorySummary;
import org.psembass.financeflow.dto.SummaryResponse;
import org.psembass.financeflow.entity.User;
import org.psembass.financeflow.enums.CategoryType;
import org.psembass.financeflow.repo.TransactionRepo;
import org.psembass.financeflow.repo.UsersRepo;
import org.psembass.financeflow.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    TransactionRepo transactionRepo;
    @Mock
    UsersRepo usersRepo;
    @InjectMocks
    TransactionService service;

    @Test
    void getSummary_shouldCalculateTotalsAndBalanceCorrectly() {
        Long userId = 1L;
        when(usersRepo.findById(userId)).thenReturn(Optional.of(new User()));
        when(transactionRepo.findCategorySummary(eq(userId), any(), any())).thenReturn(
                List.of(new CategorySummary(1L, "Salary",
                                CategoryType.INCOME, new BigDecimal(1000)),
                        new CategorySummary(2L, "Rent", CategoryType.EXPENSE,
                                new BigDecimal(500))));
        SummaryResponse summary = service.getSummary(userId, LocalDate.now().minusDays(1), LocalDate.now());
        assertEquals(new BigDecimal(1000), summary.getTotalIncome());
        assertEquals(new BigDecimal(500), summary.getTotalExpenses());
        assertEquals(new BigDecimal(500), summary.getBalance());
        assertEquals(2, summary.getByCategory().size());
    }
}
