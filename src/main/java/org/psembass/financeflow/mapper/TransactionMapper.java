package org.psembass.financeflow.mapper;

import lombok.RequiredArgsConstructor;
import org.psembass.financeflow.dto.TransactionRequest;
import org.psembass.financeflow.dto.TransactionResponse;
import org.psembass.financeflow.entity.Category;
import org.psembass.financeflow.entity.Transaction;
import org.psembass.financeflow.entity.User;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TransactionMapper {
    private final CategoryMapper categoryMapper;

    public Transaction toEntity(User user, Category category, TransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setCategory(category);
        transaction.setUserId(user.getId());
        transaction.setAmount(request.getAmount());
        transaction.setDate(request.getDate());
        transaction.setDescription(request.getDescription());
        return transaction;
    }

    public TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(transaction.getId(), transaction.getAmount(), transaction.getDescription(),
                transaction.getDate(), transaction.getUserId(), categoryMapper.toResponse(transaction.getCategory()),
                transaction.getCreatedAt());
    }
}
