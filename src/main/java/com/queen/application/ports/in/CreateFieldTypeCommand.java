package com.queen.application.ports.in;

import com.queen.adapters.web.dto.FieldsDTO;

public record CreateFieldTypeCommand(FieldsDTO fieldTypeDTO) {
}
