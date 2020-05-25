package com.jobtrail.api.models;

import com.jobtrail.api.core.enums.Priority;
import com.jobtrail.api.models.entities.JobEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class AddJob {
    private UUID assignedUserId;
    private String name;
    private String description;
    private boolean isRecurring;
    private Priority priority;
    private LocalDateTime dueDate;
    private UUID zoneId;
    private UUID parentJobId;

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

    public UUID getParentJobId() { return parentJobId; }
    public void setParentJobId(UUID value) { parentJobId = value; }

    public boolean isRecurring() { return isRecurring; }
    public void setRecurring(boolean value) { isRecurring = value; }

    public JobEntity toEntity() {
        JobEntity entity = new JobEntity();
        entity.setName(getName());
        entity.setDescription(getDescription());
        entity.setRecurring(isRecurring());
        entity.setParentJobId(getParentJobId());
        entity.setPriority(getPriority());
        entity.setDueDate(getDueDate());
        entity.setZoneId(getZoneId());
        entity.setAssignedUserId(getAssignedUserId());

        return entity;
    }
}
