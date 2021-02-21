package com.queen.domain.fields;

import javax.validation.constraints.NotNull;

public record Fields(@NotNull String id, @NotNull String fieldTypeId, @NotNull String monitorTypeId) {
}
