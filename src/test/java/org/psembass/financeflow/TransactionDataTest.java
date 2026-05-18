package org.psembass.financeflow;

import jakarta.annotation.Nonnull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.psembass.financeflow.dto.CategorySummary;
import org.psembass.financeflow.entity.Category;
import org.psembass.financeflow.entity.Transaction;
import org.psembass.financeflow.entity.User;
import org.psembass.financeflow.repo.CategoryRepo;
import org.psembass.financeflow.repo.TransactionRepo;
import org.psembass.financeflow.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionDataTest {
    private User testUser;
    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    TestEntityManager entityManager;
    private Category salaryCategory;
    private Category rentCategory;
    private Category groceriesCategory;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setName("testUser");
        testUser.setEmail("test@gmail.com");
        testUser = usersRepo.save(testUser);
        salaryCategory = categoryRepo.findByNameIgnoreCase("Salary").get();
        rentCategory = categoryRepo.findByNameIgnoreCase("Rent").get();
        groceriesCategory = categoryRepo.findByNameIgnoreCase("Groceries").get();
        entityManager.flush();
    }

    @Test
    public void findCategorySummary_shouldReturnCategorySummary() {
        Transaction salary1000 = createTransaction(testUser.getId(), LocalDate.of(2026, 5, 17), salaryCategory,
                new BigDecimal("1000.00"));
        transactionRepo.save(salary1000);

        Transaction salary2000 = createTransaction(testUser.getId(), LocalDate.of(2026, 6, 17), salaryCategory,
                new BigDecimal("2000.00"));
        transactionRepo.save(salary2000);

        Transaction rent500 = createTransaction(testUser.getId(), LocalDate.of(2026, 6, 17), rentCategory,
                new BigDecimal("500.00"));
        transactionRepo.save(rent500);

        Transaction rent700 = createTransaction(testUser.getId(), LocalDate.of(2026, 6, 17), rentCategory,
                new BigDecimal("700.00"));
        transactionRepo.save(rent700);

        Transaction groceries250 = createTransaction(testUser.getId(), LocalDate.of(2026, 6, 17), groceriesCategory,
                new BigDecimal("250.00"));
        transactionRepo.save(groceries250);

        entityManager.flush();
        entityManager.clear();

        List<CategorySummary> categorySummary = transactionRepo.findCategorySummary(testUser.getId(),
                LocalDate.of(2026, 5, 17), LocalDate.of(2026, 6, 17));
        CategorySummary salarySummary = categorySummary.stream().filter(
                category -> category.getCategoryId().equals(salaryCategory.getId())).findFirst().get();
        assertEquals(new BigDecimal("3000.00"), salarySummary.getTotal());

        CategorySummary rentSummary = categorySummary.stream().filter(
                category -> category.getCategoryId().equals(rentCategory.getId())).findFirst().get();
        assertEquals(new BigDecimal("1200.00"), rentSummary.getTotal());

        CategorySummary groceriesSummary = categorySummary.stream().filter(
                category -> category.getCategoryId().equals(groceriesCategory.getId())).findFirst().get();
        assertEquals(new BigDecimal("250.00"), groceriesSummary.getTotal());
    }

    @Nonnull
    private Transaction createTransaction(Long userId, LocalDate date, Category category, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setDate(date);
        transaction.setCategory(category);
        transaction.setAmount(amount);
        return transaction;
    }
}
