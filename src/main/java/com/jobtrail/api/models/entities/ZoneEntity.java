package com.jobtrail.api.models.entities;

import java.util.UUID;

public class ZoneEntity extends BaseEntity {
    private String name;
    private String description;
    private UUID managerId;

    public String getName() { return name; }
    public void setName(String value) { name = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { description = value; }

    public UUID getManagerId() { return managerId; }
    public void setManagerId(UUID value) { managerId = value; }
}
