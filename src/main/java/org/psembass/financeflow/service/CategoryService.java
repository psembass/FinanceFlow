package org.psembass.financeflow.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.psembass.financeflow.dto.CategoryRequest;
import org.psembass.financeflow.dto.CategoryResponse;
import org.psembass.financeflow.mapper.CategoryMapper;
import org.psembass.financeflow.repo.CategoryRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepo repo;
    private final CategoryMapper mapper;

    public List<CategoryResponse> getAllCategories() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }

    public CategoryResponse getCategory(Long id) {
        return mapper.toResponse(repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found")));
    }

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        if (repo.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException(String.format("Category with name %s already exists", request.getName()));
        }
        return mapper.toResponse(repo.save(mapper.toEntity(request)));
    }
}
