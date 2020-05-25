package com.jobtrail.api.services;

import com.jobtrail.api.dto.ZoneResponseDTO;
import com.jobtrail.api.models.AddZone;
import com.jobtrail.api.models.entities.ZoneEntity;
import com.jobtrail.api.dto.simple.SimpleZoneResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ZoneService {
    SimpleZoneResponseDTO getZone(String zoneName);
    ZoneResponseDTO getFullZone(String zoneName);
    SimpleZoneResponseDTO getZone(UUID id);
    ZoneResponseDTO getFullZone(UUID id);
    List<ZoneEntity> getAllZonesForUser(UUID userId);
    List<ZoneResponseDTO> getAllFullZonesForUser(UUID userId);
    void add(AddZone zone);
    void delete(UUID id);
}
