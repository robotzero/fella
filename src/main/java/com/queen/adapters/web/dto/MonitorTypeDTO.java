package com.queen.adapters.web.dto;

import com.queen.adapters.web.dto.FieldTypeDTO;
import com.queen.adapters.web.dto.FieldsDTO;

import java.util.List;

public record MonitorTypeDTO(String id, String name, List<FieldsDTO> fieldDTOs, List<FieldTypeDTO> fieldTypes) {
}
