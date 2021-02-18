package com.queen.domain.monitortype;

import javax.validation.constraints.NotNull;

public record MonitorType(@NotNull String id, @NotNull String name, @NotNull String userId) {
}
