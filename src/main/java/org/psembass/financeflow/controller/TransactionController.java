package org.psembass.financeflow.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.psembass.financeflow.dto.SummaryResponse;
import org.psembass.financeflow.dto.TransactionRequest;
import org.psembass.financeflow.dto.TransactionResponse;
import org.psembass.financeflow.enums.CategoryType;
import org.psembass.financeflow.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/users/{userId}/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService service;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@PathVariable Long userId,
                                                                 @Valid @RequestBody TransactionRequest transaction) {
        return ResponseEntity.ok(service.createTransaction(userId, transaction));
    }

    @GetMapping
    public ResponseEntity<Page<TransactionResponse>> getTransactions(@PathVariable Long userId,
                                                                     @RequestParam(required = false) CategoryType type,
                                                                     @RequestParam(required = false) Long categoryId,
                                                                     @RequestParam(required = false) LocalDate from,
                                                                     @RequestParam(required = false) LocalDate to,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        return ResponseEntity.ok(service.getTransactions(userId, type, categoryId, from, to, pageable));
    }

    @GetMapping("/summary")
    public ResponseEntity<SummaryResponse> getSummary(@PathVariable Long userId, @RequestParam LocalDate from,
                                                      @RequestParam LocalDate to) {
        return ResponseEntity.ok(service.getSummary(userId, from, to));
    }
}
