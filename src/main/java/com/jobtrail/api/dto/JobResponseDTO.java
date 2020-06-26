package com.jobtrail.api.dto;

import com.jobtrail.api.core.enums.Priority;
import com.jobtrail.api.models.entities.JobEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

public class JobResponseDTO {
    public JobResponseDTO(){}

    public JobResponseDTO(JobEntity entity) {
        setId(entity.getId());
        setName(entity.getName());
        setDescription(entity.getDescription());
        setPriority(entity.getPriority().name().toUpperCase());
        setDueDate(entity.getDueDate());
        setDateCreated(entity.getDateCreated());
        setDateModified(entity.getDateModified());
        setRecurring(entity.isRecurring());
        setActive(entity.isActive());
        setAssignedUserId(entity.getAssignedUserId());
        setParentJobId(entity.getParentJobId());
        setZoneId(entity.getZoneId());
        setManagerId(entity.getManagerId());
    }

    private UUID id;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;
    private boolean active;
    private UUID assignedUserId;
    private String name;
    private String description;
    private boolean isRecurring;
    private String priority;
    private LocalDateTime dueDate;
    private UUID zoneId;
    private UUID parentJobId;
    private UUID managerId;

    public UUID getAssignedUserId() {return assignedUserId;}
    public void setAssignedUserId(UUID value) {assignedUserId = value;}

    public String getName() {return name;}
    public void setName(String value) {name = value;}

    public String getDescription() {return description;}
    public void setDescription(String value) {description = value;}

    public String getPriority() { return priority; }
    public void setPriority(String value) { this.priority = value; }

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

    public UUID getId() { return id; }
    public void setId(UUID value) {id = value;}

    public LocalDateTime getDateCreated() {return dateCreated;}
    public void  setDateCreated(LocalDateTime value) {dateCreated = value;}

    public LocalDateTime getDateModified() {return dateModified;}
    public void  setDateModified(LocalDateTime value) {dateModified = value;}

    public boolean isActive() {return active;}
    public void  setActive(boolean value) {active = value;}
}
