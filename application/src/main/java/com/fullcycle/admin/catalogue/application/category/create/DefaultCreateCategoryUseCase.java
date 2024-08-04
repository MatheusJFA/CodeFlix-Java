package com.fullcycle.admin.catalogue.application.category.create;

import com.fullcycle.admin.catalogue.domain.category.Category;
import com.fullcycle.admin.catalogue.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogue.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {
    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(final CreateCategoryCommand command) {
        final var name = command.name();
        final var description = command.description();
        final var active = command.active();

        final var notification = Notification.create();

        final var category = Category.newCategory(name, description, active);
        category.validate(notification);

        if(notification.hasErrors()) {
            return Either.left(notification);
        }

        return Either.right(CreateCategoryOutput.from(this.categoryGateway.create(category)));
    }
}
