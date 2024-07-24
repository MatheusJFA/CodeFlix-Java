package com.fullcycle.admin.catalogue.domain.validation;

public abstract class Validator {
    protected final ValidationHandler handler;

    protected Validator(final ValidationHandler handler) {
        this.handler = handler;
    }

    public abstract void validate();

    protected ValidationHandler validationHandler() {
        return this.handler;
    }
}
