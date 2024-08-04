package com.fullcycle.admin.catalogue.domain.category;


import com.fullcycle.admin.catalogue.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogue.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class CategoryTest {
    @Test
    public void givenAValidParams_whenCallNewCategory_thenInstantiateACategory() {
        final var expectedName = "Movies";
        final var expectedDescription = "Most watched Category";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedIsActive, category.isActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());
    }

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedDescription = "Most watched Category";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var exception = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameContainingOnlySpaces_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final var expectedName = "  ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedDescription = "Most watched Category";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var exception = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameContainingLessThan3Characters_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final var expectedName = "ab";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must have between 3 and 255 characters";
        final var expectedDescription = "Most watched Category";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var exception = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameContainingMoreThan255Characters_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final var expectedName = "abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcababc";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must have between 3 and 255 characters";
        final var expectedDescription = "Most watched Category";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var exception = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }

    @Test
    public void givenAValidEmptyDescription_whenCallNewCategory_thenInstantiateACategory() {
        final var expectedName = "Movies";
        final var expectedDescription = "   ";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedIsActive, category.isActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());
    }

    @Test
    public void givenAValidFalseActiveCategory_whenCallNewCategory_thenShouldReturnAnInactiveCategory() {
        final var expectedName = "Movies";
        final var expectedDescription = "Most watched Category";
        final var expectedIsActive = false;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedIsActive, category.isActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNotNull(category.getDeletedAt());
    }

    @Test
    public void givenAValidActiveCategory_whenCallDeactivate_thenShouldReturnAnInactiveCategory() {
        final var expectedName = "Movies";
        final var expectedDescription = "Most watched Category";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var updatedAt = category.getUpdatedAt();
        final var createdAt = category.getCreatedAt();
        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));

        Assertions.assertTrue(category.isActive());

        final var changedCategory = category.deactivate();
        Assertions.assertDoesNotThrow(() -> changedCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertFalse(changedCategory.isActive());
        Assertions.assertEquals(createdAt, changedCategory.getCreatedAt());
        Assertions.assertTrue(changedCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(changedCategory.getDeletedAt());
    }

    @Test
    public void givenAValidInactiveCategory_whenCallActivate_thenShouldReturnAnValidActiveCategory() {
        final var expectedName = "Movies";
        final var expectedDescription = "Most watched Category";
        final var expectedIsActive = false;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));
        final var updatedAt = category.getUpdatedAt();
        final var createdAt = category.getCreatedAt();

        Assertions.assertFalse(category.isActive());

        final var changedCategory = category.activate();

        Assertions.assertDoesNotThrow(() -> changedCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertTrue(changedCategory.isActive());
        Assertions.assertEquals(createdAt, changedCategory.getCreatedAt());
        Assertions.assertTrue(changedCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(changedCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenShouldReturnAChangedCategory() {
        final var expectedName = "Movies";
        final var expectedDescription = "Most watched Category";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));
        Assertions.assertTrue(category.isActive());
        Assertions.assertNull(category.getDeletedAt());

        final var newName = "Movies II";
        final var newDescription = "Another movie category.";

        final var changedCategory = category.update(newName, newDescription, true);
        Assertions.assertDoesNotThrow(() -> changedCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertTrue(changedCategory.isActive());
        Assertions.assertNull(changedCategory.getDeletedAt());

        Assertions.assertEquals(changedCategory.getName(), newName);
        Assertions.assertEquals(changedCategory.getDescription(), newDescription);
    }

    @Test
    public void givenAValidCategory_whenCallUpdateToInactive_thenShouldReturnAChangedCategory() {
        final var expectedName = "Movies";
        final var expectedDescription = "Most watched Category";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));
        Assertions.assertTrue(category.isActive());
        Assertions.assertNull(category.getDeletedAt());

        final var newName = "Movies II";
        final var newDescription = "Another movie category.";

        final var changedCategory = category.update(newName, newDescription, false);
        Assertions.assertDoesNotThrow(() -> changedCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertFalse(changedCategory.isActive());
        Assertions.assertNotNull(changedCategory.getDeletedAt());

        Assertions.assertEquals(changedCategory.getName(), newName);
        Assertions.assertEquals(changedCategory.getDescription(), newDescription);
    }

    @Test
    public void givenAValidCategory_whenCallUpdateWithInvalidParams_thenShouldReturnAChangedCategory() {
        final var expectedName = "Movies";
        final var expectedDescription = "Most watched Category";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));
        final var updatedAt = category.getUpdatedAt();
        final var createdAt = category.getCreatedAt();

        final var newName = "";
        final var newDescription = "Another movie category.";

        final var changedCategory = category.update(newName, newDescription, true);

        Assertions.assertEquals(category.getId(), changedCategory.getId());
        Assertions.assertEquals(changedCategory.getName(), newName);
        Assertions.assertEquals(changedCategory.getDescription(), newDescription);
        Assertions.assertTrue(changedCategory.isActive());
        Assertions.assertEquals(createdAt, changedCategory.getCreatedAt());
        Assertions.assertNotEquals(updatedAt, changedCategory.getUpdatedAt());



    }
}
