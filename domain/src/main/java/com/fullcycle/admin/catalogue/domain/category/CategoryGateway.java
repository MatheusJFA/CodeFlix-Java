package com.fullcycle.admin.catalogue.domain.category;

import com.fullcycle.admin.catalogue.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {
        Category create(Category category);

        Optional<Category> findById(String id);

        Pagination<Category> findAll(CategorySearchQuery query);

        void deleteById(String id);

        Category update(Category category);
}
