package com.queen.domain.fieldtype;

import javax.validation.constraints.NotNull;

public record FieldType(@NotNull String id, @NotNull String name, @NotNull FieldTypeValue fieldTypeValue) {
}
