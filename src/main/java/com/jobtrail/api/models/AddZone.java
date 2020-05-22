package com.jobtrail.api.models;

import com.jobtrail.api.models.entities.ZoneEntity;

import java.util.UUID;

public class AddZone {
    private String name;
    private String description;
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
