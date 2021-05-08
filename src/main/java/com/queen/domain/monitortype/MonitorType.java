package com.queen.domain.monitortype;

import com.queen.domain.fieldtype.FieldType;

import java.util.List;

public record MonitorType(String id, String name, List<FieldType> fieldTypes) {
}
