package com.fullcycle.admin.catalogue.application.category.create;

import com.fullcycle.admin.catalogue.domain.category.Category;
import com.fullcycle.admin.catalogue.domain.category.CategoryID;

public record CreateCategoryCommand(String name, String description, boolean active) {
    public static CreateCategoryCommand with(final String name, final String description, final boolean active) {
        return new CreateCategoryCommand(name, description, active);
    }
}


