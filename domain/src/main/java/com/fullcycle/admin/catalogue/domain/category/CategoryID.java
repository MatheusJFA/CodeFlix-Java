package com.fullcycle.admin.catalogue.domain.category;

import com.fullcycle.admin.catalogue.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class CategoryID extends Identifier {
    private final String id;

    public CategoryID(final String id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    public static CategoryID unique() {
        return CategoryID.from(UUID.randomUUID());
    }

    public static CategoryID from(final String id) {
        return new CategoryID(id);
    }

    public static CategoryID from(final UUID id) {
        return new CategoryID(id.toString().toLowerCase());
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryID that = (CategoryID) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
