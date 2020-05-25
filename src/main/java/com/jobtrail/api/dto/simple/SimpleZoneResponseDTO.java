package com.jobtrail.api.dto.simple;

import com.jobtrail.api.core.ApiResponse;
import com.jobtrail.api.models.entities.ZoneEntity;

public class SimpleZoneResponseDTO extends ZoneEntity implements ApiResponse {
    public SimpleZoneResponseDTO() {}

    public SimpleZoneResponseDTO(ZoneEntity entity) {
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
