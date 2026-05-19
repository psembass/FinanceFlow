package org.psembass.financeflow;

import org.junit.jupiter.api.*;
import org.psembass.financeflow.dto.*;
import org.psembass.financeflow.enums.CategoryType;
import org.psembass.financeflow.repo.TransactionRepo;
import org.psembass.financeflow.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FinanceFlowApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;
    private static final String salaryName = "Salary";
    private Long userId;
    private Long salaryCategoryId;
    // repo for cleanup
    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    UsersRepo usersRepo;

    @Test
    @Order(1)
    void contextLoads() {
    }

    @Test
    @Order(2)
    void createUser_returnsUserResponse() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Test User");
        userRequest.setEmail("test@gmail.com");
        ResponseEntity<UserResponse> userResponse = restTemplate.postForEntity("/api/users", userRequest,
                UserResponse.class);
        assertEquals(userRequest.getName(), userResponse.getBody().getName());
        assertEquals(userRequest.getEmail(), userResponse.getBody().getEmail());
        userId = userResponse.getBody().getId();
    }

    @Test
    @Order(3)
    void readCategory_returnsCategoryResponse() {
        ResponseEntity<List<CategoryResponse>> categoriesResponse = restTemplate.exchange(
                "/api/categories",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CategoryResponse>>() {}
        );
        CategoryResponse salaryCategory = categoriesResponse.getBody().stream().filter(
                category -> category.getName().equals(salaryName)).findFirst().get();
        assertEquals(CategoryType.INCOME, salaryCategory.getType());
        salaryCategoryId = salaryCategory.getId();
    }

    @Test
    @Order(4)
    void createTransaction_returnsTransactionResponse() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(new BigDecimal("1000.00"));
        transactionRequest.setDate(LocalDate.of(2026, 5, 19));
        transactionRequest.setCategoryId(salaryCategoryId);
        transactionRequest.setDescription("test transaction");
        ResponseEntity<TransactionResponse> transactionResponseEntity = restTemplate.postForEntity(
                String.format("/api/users/%s/transactions", userId), transactionRequest, TransactionResponse.class);
        System.out.println("transaction created "+transactionResponseEntity.getBody());
        System.out.println("transaction created id "+transactionResponseEntity.getBody().getId());
        assertEquals(new BigDecimal("1000.00"), transactionResponseEntity.getBody().getAmount());
        assertEquals(LocalDate.of(2026, 5, 19), transactionResponseEntity.getBody().getDate());
        // todo check other fields
    }

    @Test
    @Order(5)
    void transactionSummary_returns1000() {
        ResponseEntity<SummaryResponse> summaryResponseEntity = restTemplate.getForEntity(
                "/api/users/{id}/transactions/summary?from={from}&to={to}", SummaryResponse.class, userId, LocalDate.of(2026, 5, 19), LocalDate.of(2026, 5, 19));
        assertEquals(new BigDecimal("1000.00"), summaryResponseEntity.getBody().getTotalIncome());
        assertEquals(new BigDecimal("1000.00"), summaryResponseEntity.getBody().getBalance());
        // todo check other fields
    }

    @AfterAll
    void cleanup() {
        transactionRepo.deleteAll();
        usersRepo.deleteAll();
    }
}
