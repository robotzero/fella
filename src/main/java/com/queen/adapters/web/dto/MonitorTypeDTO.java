package com.queen.adapters.web.dto;

import java.util.List;

public record MonitorTypeDTO(String id, String name, List<FieldsDTO> fieldDTOs, List<FieldTypeDTO> fieldTypes, String userId) {
}
