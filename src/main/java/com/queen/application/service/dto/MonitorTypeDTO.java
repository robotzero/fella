package com.queen.application.service.dto;

import java.util.List;

public record MonitorTypeDTO(String id, String name, List<FieldsDTO> fieldDTOs, String userId) {}
