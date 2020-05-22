package com.jobtrail.api.services;

import com.jobtrail.api.dto.ZoneResponseDTO;
import com.jobtrail.api.models.AddZone;

import java.util.List;
import java.util.UUID;

public interface ZoneService {
    ZoneResponseDTO getById(UUID id);
    List<ZoneResponseDTO> getAllForUser(UUID userId);
    void add(AddZone zone);
}
