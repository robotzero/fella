package com.queen.adapters.web;

import java.util.List;

public record MonitorTypeDTO(String id, String name, List<FieldsDTO> fieldDTOs, List<FieldTypeDTO> fieldTypes) {
}
