package com.fullcycle.admin.catalogue.application;

public abstract class UseCase<IN, OUT> {
    public abstract OUT execute(IN in);
}
