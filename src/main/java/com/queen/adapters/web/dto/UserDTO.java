package com.queen.adapters.web.dto;

import java.util.UUID;

public record UserDTO(UUID id, String username, String email) {}
