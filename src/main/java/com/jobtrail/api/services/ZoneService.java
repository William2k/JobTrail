package com.jobtrail.api.services;

import com.jobtrail.api.dto.full.FullZoneResponseDTO;
import com.jobtrail.api.models.AddZone;
import com.jobtrail.api.models.entities.ZoneEntity;
import com.jobtrail.api.dto.ZoneResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ZoneService {
    ZoneResponseDTO getZone(UUID id);
    FullZoneResponseDTO getFullZone(UUID id);
    List<ZoneEntity> getAllZonesForUser(UUID userId);
    List<FullZoneResponseDTO> getAllFullZonesForUser(UUID userId);
    UUID add(AddZone zone);
    void delete(UUID id);
}
