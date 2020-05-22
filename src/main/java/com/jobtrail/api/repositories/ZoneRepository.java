package com.jobtrail.api.repositories;

import com.jobtrail.api.models.entities.ZoneEntity;

import java.util.List;
import java.util.UUID;

public interface ZoneRepository extends BaseRepository<ZoneEntity> {
    ZoneEntity getByName(String name);
    List<ZoneEntity> getAll(UUID userId);
    List<ZoneEntity> getAll(String username);
}
