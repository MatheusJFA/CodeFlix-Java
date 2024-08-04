package com.fullcycle.admin.catalogue.domain.validation.handler;

import com.fullcycle.admin.catalogue.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogue.domain.validation.Error;
import com.fullcycle.admin.catalogue.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {
    private final List<Error> errors;

    public Notification(List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create(final Error error) {
        return new Notification(new ArrayList<Error>()).append(error);
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    @Override
    public Notification append(Error error) {
        this.errors.add(error);

        return this;
    }

    @Override
    public Notification append(ValidationHandler handler) {
        this.errors.addAll(handler.getErrors());
        return this;
    }

    @Override
    public Notification validate(final Validation validation) {
        try {
            validation.validate();
        } catch (DomainException ex) {
            this.errors.addAll(ex.getErrors());
        } catch (final Throwable throwable) {
            this.errors.add(new Error(throwable.getMessage()));
        }

        return this;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }

    @Override
    public boolean hasErrors() {
        return ValidationHandler.super.hasErrors();
    }
}
