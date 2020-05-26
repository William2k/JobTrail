package com.jobtrail.api.dto.full;

import com.jobtrail.api.core.enums.Priority;
import com.jobtrail.api.dto.FullBaseDTO;
import com.jobtrail.api.models.entities.JobEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class FullJobResponseDTO extends FullBaseDTO {
    public FullJobResponseDTO() { }

    public FullJobResponseDTO(JobEntity entity) {
        setId(entity.getId());
        setName(entity.getName());
        setDescription(entity.getDescription());
        setDueDate(entity.getDueDate());
        setPriority(entity.getPriority());
    }

    private UUID id;
    private FullUserResponseDTO assignedUser;
    private String name;
    private String description;
    private Priority priority;
    private LocalDateTime dueDate;
    private FullZoneResponseDTO zone;
    private FullJobResponseDTO parentJob;
    private FullUserResponseDTO manager;

    public UUID getId() { return id; }
    public void setId(UUID value) {id = value;}

    public FullUserResponseDTO getAssignedUser() {return assignedUser;}
    public void setAssignedUser(FullUserResponseDTO value) {assignedUser = value;}

    public String getName() {return name;}
    public void setName(String value) {name = value;}

    public String getDescription() {return description;}
    public void setDescription(String value) {description = value;}

    public Priority getPriority() { return priority; }
    public void setPriority(Priority value) { this.priority = value; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime value) { dueDate = value; }

    public FullZoneResponseDTO getZone() { return zone; }
    public void setZone(FullZoneResponseDTO value) { zone = value; }

    public FullJobResponseDTO getParentJob() { return parentJob; }
    public void setParentJob(FullJobResponseDTO value) { parentJob = value; }

    public FullUserResponseDTO getManager() { return manager; }
    public void setManager(FullUserResponseDTO value) { manager = value; }
}
