package com.fullcycle.admin.catalogue.domain.category;

import com.fullcycle.admin.catalogue.domain.validation.Error;
import com.fullcycle.admin.catalogue.domain.validation.ValidationHandler;
import com.fullcycle.admin.catalogue.domain.validation.Validator;

public class CategoryValidator extends Validator {
    private static final int NAME_MAX_LENGTH = 255;
    private static final int NAME_MIN_LENGTH = 3;

    private final Category category;

    public CategoryValidator(Category category, ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {
        this.checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.category.getName();

        if (name == null) {
            this.handler.append(new Error("'name' should not be null"));
        }

        if (name.isBlank() || name.isEmpty()) {
            this.handler.append(new Error("'name' should not be empty"));
        }

        final int length = name.trim().length();

        if(length < NAME_MIN_LENGTH || length > NAME_MAX_LENGTH) {
            this.handler.append(new Error("'name' must have between " +NAME_MIN_LENGTH+ " and " +NAME_MAX_LENGTH+ " characters"));
        }
    }
}
