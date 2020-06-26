package com.jobtrail.api.models.entities;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class ZoneEntity extends BaseEntity {
    @NotNull(message = "Name cannot be null")
    private String name;

    @Size(min = 3, message = "Description must be at least 3 characters")
    private String description;

    private boolean isPublic;

    @NotNull(message = "Manager Id cannot be null")
    private UUID managerId;

    private UUID parentZoneId;

    public String getName() { return name; }
    public void setName(String value) { name = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { description = value; }

    public boolean isPublic() { return isPublic; }
    public void setPublic(boolean value) { isPublic = value;}

    public UUID getManagerId() { return managerId; }
    public void setManagerId(UUID value) { managerId = value; }

    public UUID getParentZoneId() { return parentZoneId; }
    public void setParentZoneId(UUID value) { parentZoneId = value; }
}
