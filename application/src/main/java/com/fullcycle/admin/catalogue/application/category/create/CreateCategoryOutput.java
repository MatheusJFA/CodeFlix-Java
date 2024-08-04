package com.fullcycle.admin.catalogue.application.category.create;

import com.fullcycle.admin.catalogue.domain.category.Category;
import com.fullcycle.admin.catalogue.domain.category.CategoryID;

public record CreateCategoryOutput(CategoryID id) {
    public static CreateCategoryOutput from(final Category category) {
        return new CreateCategoryOutput(category.getId());
    }
}
