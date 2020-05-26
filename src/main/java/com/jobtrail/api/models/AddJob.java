package com.jobtrail.api.models;

import com.jobtrail.api.core.enums.Priority;
import com.jobtrail.api.models.entities.JobEntity;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

public class AddJob {
    private UUID assignedUserId;

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 25, message = "Name must be between 3 and 25 characters")
    private String name;

    @Size(min = 3, message = "Description must be at least 3 characters")
    private String description;

    private boolean isRecurring;

    @NotNull(message = "Priority cannot be null")
    private Priority priority;

    @NotNull(message = "Due date cannot be null")
    @Future(message = "Due date must be a future date/time")
    private LocalDateTime dueDate;

    @NotNull(message = "Zone Id cannot be null")
    private UUID zoneId;

    private UUID parentJobId;

    @NotNull(message = "Manager Id cannot be null")
    private UUID managerId;

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

    public UUID getManagerId() { return managerId; }
    public void setManagerId(UUID value) { managerId = value; }

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
        entity.setManagerId(getManagerId());

        return entity;
    }
}
