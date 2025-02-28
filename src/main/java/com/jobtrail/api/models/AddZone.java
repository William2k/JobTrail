package com.jobtrail.api.models;

import com.jobtrail.api.models.entities.ZoneEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class AddZone {
    @NotNull(message = "Name cannot be null")
    private String name;

    @Size(min = 3, message = "Description must be at least 3 characters")
    private String description;

    @NotNull(message = "Manager Id cannot be null")
    private UUID managerId;

    private UUID parentZoneId;

    public String getName() { return name; }
    public void setName(String value) { name = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { description = value; }

    public UUID getManagerId() { return managerId; }
    public void setManagerId(UUID value) { managerId = value; }

    public UUID getParentZoneId() { return parentZoneId; }
    public void setParentZoneId(UUID value) { parentZoneId = value; }

    public ZoneEntity toEntity() {
        ZoneEntity entity = new ZoneEntity();
        entity.setName(getName());
        entity.setDescription(getDescription());
        entity.setManagerId(getManagerId());
        entity.setParentZoneId(getParentZoneId());

        return entity;
    }
}
