package com.queen.application.ports.in;

import com.queen.adapters.web.FieldsDTO;

public record CreateFieldTypeCommand(FieldsDTO fieldTypeDTO) {
}
