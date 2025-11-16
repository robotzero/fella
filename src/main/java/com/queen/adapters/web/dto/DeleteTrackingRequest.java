package com.queen.adapters.web.dto;

import java.util.Set;
import java.util.UUID;

public record DeleteTrackingRequest(Set<UUID> trackingIds) {
}
