package com.jobtrail.api.dto;

import com.jobtrail.api.core.enums.Priority;
import com.jobtrail.api.models.User;
import com.jobtrail.api.models.entities.JobEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class JobResponseDTO {
    public JobResponseDTO() { }

    public JobResponseDTO(JobEntity entity) {
        setId(entity.getId());
        setName(entity.getName());
        setDescription(entity.getDescription());
        setDueDate(entity.getDueDate());
        setPriority(entity.getPriority());
    }

    private UUID id;
    private UserResponseDTO assignedUser;
    private String name;
    private String description;
    private Priority priority;
    private LocalDateTime dueDate;
    private ZoneResponseDTO zone;
    private JobResponseDTO parentJob;

    public UUID getId() { return id; }
    public void setId(UUID value) {id = value;}

    public UserResponseDTO getAssignedUser() {return assignedUser;}
    public void setAssignedUser(UserResponseDTO value) {assignedUser = value;}

    public String getName() {return name;}
    public void setName(String value) {name = value;}

    public String getDescription() {return description;}
    public void setDescription(String value) {description = value;}

    public Priority getPriority() { return priority; }
    public void setPriority(Priority value) { this.priority = value; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime value) { dueDate = value; }

    public ZoneResponseDTO getZone() { return zone; }
    public void setZone(ZoneResponseDTO value) { zone = value; }

    public JobResponseDTO getParentJob() { return parentJob; }
    public void setParentJob(JobResponseDTO value) { parentJob = value; }
}
