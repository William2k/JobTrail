package com.jobtrail.api.models.entities;

import com.jobtrail.api.core.enums.Priority;

import java.time.LocalDateTime;
import java.util.UUID;

public class JobEntity extends BaseEntity {
    private UUID assignedUserId;
    private String name;
    private String description;
    private Priority priority;
    private LocalDateTime dueDate;
    private UUID zoneId;
    private UUID linkId;

    public UUID getAssignedUserId() {return assignedUserId;}
    public void setAssignedUserId(UUID value) {assignedUserId = value;}

    public String getName() {return name;}
    public void setName(String value) {name = value;}

    public String getDescription() {return description;}
    public void setDescription(String value) {description = value;}

    public Priority getPriority() { return priority; }
    public void setPriority(Priority value) { this.priority = value; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime value) { dueDate = value; }

    public UUID getZoneId() { return zoneId; }
    public void setZoneId(UUID value) { zoneId = value; }

    public UUID getLinkId() { return linkId; }
    public void setLinkId(UUID value) { linkId = value; }
}
