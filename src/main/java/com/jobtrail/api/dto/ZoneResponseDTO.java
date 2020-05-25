package com.jobtrail.api.dto;

import com.jobtrail.api.models.entities.ZoneEntity;

public class ZoneResponseDTO extends ZoneEntity {
    public ZoneResponseDTO() {}

    public ZoneResponseDTO(ZoneEntity entity) {
        setId(entity.getId());
        setName(entity.getName());
        setDescription(entity.getDescription());
        setManagerId(entity.getManagerId());
        setParentZoneId(entity.getParentZoneId());
        setActive(entity.isActive());
        setDateCreated(entity.getDateCreated());
        setDateModified(entity.getDateModified());
    }
}
