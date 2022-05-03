package com.queen.adapters.web.dto;

import java.util.List;

public record MonitorTypeRequest(String name, List<Integer> fieldTypes) {
}

