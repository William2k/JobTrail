package com.jobtrail.api.dto;

import com.jobtrail.api.models.entities.ZoneEntity;

import java.util.UUID;

public class ZoneResponseDTO {
    public ZoneResponseDTO() { }

    public ZoneResponseDTO(ZoneEntity entity) {
        setId(entity.getId());
        setName(entity.getName());
        setDescription(entity.getDescription());
    }

    private UUID id;
    private String name;
    private String description;
    private UserResponseDTO manager;
    private ZoneResponseDTO parentZone;

    public UUID getId() { return id; }
    public void setId(UUID value) {id = value;}

    public String getName() { return name; }
    public void setName(String value) { name = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { description = value; }

    public UserResponseDTO getManager() { return manager; }
    public void setManager(UserResponseDTO value) { manager = value; }

    public ZoneResponseDTO getParentZone() { return parentZone; }
    public void setParentZone(ZoneResponseDTO value) { parentZone = value; }
}
