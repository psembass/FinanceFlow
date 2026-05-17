package org.psembass.financeflow;

import org.junit.jupiter.api.Test;
import org.psembass.financeflow.controller.TransactionController;
import org.psembass.financeflow.dto.CategoryResponse;
import org.psembass.financeflow.dto.TransactionResponse;
import org.psembass.financeflow.enums.CategoryType;
import org.psembass.financeflow.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    TransactionService service;

    @Test
    void createTransaction_shouldReturn201() throws Exception {
        BigDecimal amount = new BigDecimal("10.00");
        LocalDate today = LocalDate.now();
        when(service.createTransaction(any(), any())).thenReturn(
                new TransactionResponse(1L, amount, "Test transaction", today, 1L,
                        new CategoryResponse(1L, "Test category", CategoryType.INCOME), OffsetDateTime.now()));

        mockMvc.perform(post("/api/users/1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"amount": "10.00",
                                "description": "Test",
                                "date" : "%s",
                                "categoryId": "1"}))}
                                """.formatted(today.toString())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value("10.0"));
        // todo check the rest of the fields
    }
}
