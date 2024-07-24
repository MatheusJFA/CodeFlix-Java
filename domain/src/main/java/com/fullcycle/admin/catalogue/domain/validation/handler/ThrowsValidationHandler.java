package com.fullcycle.admin.catalogue.domain.validation.handler;

import com.fullcycle.admin.catalogue.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogue.domain.validation.Error;
import com.fullcycle.admin.catalogue.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(Error error) {
        throw DomainException.with(error);
    }

    @Override
    public ValidationHandler append(ValidationHandler handler) {
        throw DomainException.with(handler.getErrors());
    }

    @Override
    public <T> T validate(final Validation<T> validation) {
        try {
            return validation.validate();
        } catch(final Exception exception) {
            throw DomainException.with(new Error(exception.getMessage()));
        }
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }
}
