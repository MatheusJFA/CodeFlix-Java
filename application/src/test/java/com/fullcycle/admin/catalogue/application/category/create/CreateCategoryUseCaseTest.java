package com.fullcycle.admin.catalogue.application.category.create;

import com.fullcycle.admin.catalogue.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogue.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    @Test
    public void givenAValidCommand_WhenCallsCreateCategoryCommand_shouldReturnCategoryId() {
        final var expectedName = "Movies";
        final var expectedDescription = "Most watched category";
        final var expectedActive = true;

        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedActive);

        Mockito.when(gateway.create(Mockito.any()))
                    .thenAnswer(returnsFirstArg());

        final var output = useCase.execute(command);

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.id());


        Mockito.verify(gateway, Mockito.times(1)).create(Mockito.argThat(c ->
                    Objects.equals(expectedName, c.getName())
                        && Objects.equals(expectedDescription, c.getDescription())
                        && Objects.equals(expectedActive, c.isActive())
                        && Objects.nonNull(c.getId())
                        && Objects.nonNull(c.getCreatedAt())
                        && Objects.nonNull(c.getUpdatedAt())
                        && Objects.isNull(c.getDeletedAt())));
    }


    @Test
    public void givenAnInvalidName_WhenCallsCreateCategoryCommand_shouldReturnDomainException() {
        final var expectedName = "";
        final var expectedDescription = "Most watched category";
        final var expectedActive = true;
        final var expectedException = "'name' should not be empty";

        Mockito.when(gateway.create(Mockito.any()))
                .thenAnswer(returnsFirstArg());

        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedActive);
        final var exception = Assertions.assertThrows(DomainException.class, () -> useCase.execute(command));

        Assertions.assertEquals(expectedException, exception.getMessage());
        Mockito.verify(gateway, Mockito.times(0)).create(Mockito.any());
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_WhenCallsCreateCategoryCommand_shouldReturnInactiveCategoryId() {
        final var expectedName = "Movies";
        final var expectedDescription = "Most watched category";
        final var expectedActive = false;

        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedActive);

        final var output = useCase.execute(command);

        Mockito.when(gateway.create(Mockito.any()))
                .thenAnswer(returnsFirstArg());

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.id());

        Mockito.verify(gateway, Mockito.times(1)).create(Mockito.argThat(c ->
                Objects.equals(expectedName, c.getName())
                        && Objects.equals(expectedDescription, c.getDescription())
                        && Objects.equals(expectedActive, c.isActive())
                        && Objects.nonNull(c.getId())
                        && Objects.nonNull(c.getCreatedAt())
                        && Objects.nonNull(c.getUpdatedAt())
                        && Objects.nonNull(c.getDeletedAt())));
    }

    @Test
    public void givenAValidCommand_WhenGatewayThrowsRandomException_shouldReturnAnException() {
        final var expectedName = "Movies";
        final var expectedDescription = "Most watched category";
        final var expectedActive = false;

        final var expectedException = "Gateway error";

        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedActive);

        Mockito.when(gateway.create(Mockito.any()))
                .thenThrow(new RuntimeException("Gateway error"));

        final var exception = Assertions.assertThrows(RuntimeException.class, () -> useCase.execute(command));

        Assertions.assertEquals(expectedException, exception.getMessage());

        Mockito.verify(gateway, Mockito.times(1)).create(Mockito.argThat(c ->
                Objects.equals(expectedName, c.getName())
                        && Objects.equals(expectedDescription, c.getDescription())
                        && Objects.equals(expectedActive, c.isActive())
                        && Objects.nonNull(c.getId())
                        && Objects.nonNull(c.getCreatedAt())
                        && Objects.nonNull(c.getUpdatedAt())
                        && Objects.nonNull(c.getDeletedAt())));
    }
}
