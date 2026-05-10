package org.psembass.financeflow.repo;

import org.psembass.financeflow.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    boolean existsByNameIgnoreCase(String name);
}
