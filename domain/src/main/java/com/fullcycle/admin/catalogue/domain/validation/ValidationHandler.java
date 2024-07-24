package com.fullcycle.admin.catalogue.domain.validation;

import java.util.List;

public interface ValidationHandler {
    ValidationHandler append(Error error);
    ValidationHandler append(ValidationHandler handler);

    <T> T validate(Validation<T> validation);

    List<Error> getErrors();

    default boolean hasErrors() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    interface Validation<T> {
        T validate();
    }
}
