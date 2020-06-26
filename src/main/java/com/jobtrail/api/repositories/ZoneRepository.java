package com.jobtrail.api.repositories;

import com.jobtrail.api.models.entities.ZoneEntity;

import java.util.List;
import java.util.UUID;

public interface ZoneRepository extends BaseRepository<ZoneEntity> {
    boolean exists(String name, UUID parentZoneId);
    ZoneEntity getByName(String name, UUID parentZoneId);
    List<ZoneEntity> getAll(UUID userId);
    List<ZoneEntity> getAll(String username);
    void addUserToZone(UUID userId, UUID zoneId, String role);
}
