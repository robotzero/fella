package com.queen.domain.monitortype;

import com.queen.domain.fieldtype.FieldType;

import javax.validation.constraints.NotNull;
import java.util.List;

public record MonitorType(@NotNull String id, @NotNull String name, @NotNull List<FieldType> fieldTypes) {
}
