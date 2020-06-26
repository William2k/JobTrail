package com.jobtrail.api.dto.full;

import com.jobtrail.api.dto.FullBaseDTO;
import com.jobtrail.api.models.entities.ZoneEntity;

import java.util.UUID;

public class FullZoneResponseDTO extends FullBaseDTO {
    public FullZoneResponseDTO() { }

    public FullZoneResponseDTO(ZoneEntity entity) {
        setId(entity.getId());
        setName(entity.getName());
        setDescription(entity.getDescription());
        setId(entity.getId());
        setName(entity.getName());
        setDescription(entity.getDescription());
        setActive(entity.isActive());
        setPublic(entity.isPublic());
        setDateCreated(entity.getDateCreated());
        setDateModified(entity.getDateModified());
    }

    private UUID id;
    private String name;
    private String description;
    private boolean isPublic;
    private FullUserResponseDTO manager;
    private FullZoneResponseDTO parentZone;

    public UUID getId() { return id; }
    public void setId(UUID value) {id = value;}

    public String getName() { return name; }
    public void setName(String value) { name = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { description = value; }

    public boolean isPublic() { return isPublic; }
    public void setPublic(boolean value) { isPublic = value;}

    public FullUserResponseDTO getManager() { return manager; }
    public void setManager(FullUserResponseDTO value) { manager = value; }

    public FullZoneResponseDTO getParentZone() { return parentZone; }
    public void setParentZone(FullZoneResponseDTO value) { parentZone = value; }
}
