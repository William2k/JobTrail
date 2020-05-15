package com.jobtrail.api.models.entities;

import com.jobtrail.api.core.enums.Priority;

import java.util.UUID;

public class JobEntity extends BaseEntity {
    private UUID assignedUserId;
    private String name;
    private String description;
    private Priority priority;
    private UUID clientId;

    public UUID getAssignedUserId() {return assignedUserId;}
    public void setAssignedUserId(UUID value) {assignedUserId = value;}

    public String getName() {return name;}
    public void setName(String value) {name = value;}

    public String getDescription() {return description;}
    public void setDescription(String value) {description = value;}

    public UUID getClientId() {return clientId;}
    public void setClientId(UUID value) { clientId = value; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority value) { this.priority = value; }
}
