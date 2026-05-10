package org.psembass.financeflow.mapper;

import org.psembass.financeflow.dto.CategoryRequest;
import org.psembass.financeflow.dto.CategoryResponse;
import org.psembass.financeflow.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getType());
    }

    public Category toEntity(CategoryRequest request) {
        Category entity = new Category();
        entity.setName(request.getName());
        entity.setType(request.getType());
        return entity;
    }
}
