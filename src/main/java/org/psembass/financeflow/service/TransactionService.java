package org.psembass.financeflow.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.psembass.financeflow.dto.CategorySummary;
import org.psembass.financeflow.dto.SummaryResponse;
import org.psembass.financeflow.dto.TransactionRequest;
import org.psembass.financeflow.dto.TransactionResponse;
import org.psembass.financeflow.entity.Category;
import org.psembass.financeflow.entity.Transaction;
import org.psembass.financeflow.entity.User;
import org.psembass.financeflow.enums.CategoryType;
import org.psembass.financeflow.mapper.TransactionMapper;
import org.psembass.financeflow.repo.CategoryRepo;
import org.psembass.financeflow.repo.TransactionRepo;
import org.psembass.financeflow.repo.UsersRepo;
import org.psembass.financeflow.specification.TransactionSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {
    private final TransactionRepo repo;
    private final UsersRepo usersRepo;
    private final CategoryRepo categoryRepo;
    private final TransactionMapper mapper;

    @Transactional
    public TransactionResponse createTransaction(Long userId,
                                                 TransactionRequest request) {
        User user = usersRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        Category category = categoryRepo.findById(request.getCategoryId()).orElseThrow(() -> new EntityNotFoundException("Category with id " + request.getCategoryId() + " not found"));
        Transaction entity = mapper.toEntity(user, category, request);
        return mapper.toResponse(repo.save(entity));
    }

    public Page<TransactionResponse> getTransactions(Long userId,
                                                     CategoryType type,
                                                     Long categoryId,
                                                     LocalDate from,
                                                     LocalDate to,
                                                     Pageable pageable) {
        usersRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        Specification<Transaction> specification = TransactionSpecification.forUser(userId).and(TransactionSpecification.withCategoryFetch());
        if (categoryId != null) {
            specification = specification.and(TransactionSpecification.hasCategory(categoryId));
        } else if (type != null) {
            specification = specification.and(TransactionSpecification.hasType(type));
        }
        if (from != null) {
            specification = specification.and(TransactionSpecification.fromDate(from));
        }
        if (to != null) {
            specification = specification.and(TransactionSpecification.toDate(to));
        }
        return repo.findAll(specification, pageable).map(mapper::toResponse);
    }

    public SummaryResponse getSummary(Long userId, LocalDate from, LocalDate to) {
        usersRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("From must be before to date");
        }
        List<CategorySummary> categorySummary = repo.findCategorySummary(userId, from, to);
        BigDecimal totalIncome = categorySummary.stream()
                .filter(c -> c.getType() == CategoryType.INCOME)
                .map(CategorySummary::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalExpenses = categorySummary.stream()
                .filter(c -> c.getType() == CategoryType.EXPENSE)
                .map(CategorySummary::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new SummaryResponse(from, to, totalIncome, totalExpenses, totalIncome.subtract(totalExpenses), categorySummary);
    }
}
