package com.queen.adapters.web;

import javax.validation.constraints.NotNull;

public record FieldsDTO(@NotNull String id, @NotNull String monitorTypeId, @NotNull String fieldTypeId) {
}
