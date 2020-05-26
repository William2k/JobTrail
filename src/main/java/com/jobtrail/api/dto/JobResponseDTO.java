package com.jobtrail.api.dto;

import com.jobtrail.api.models.entities.JobEntity;

public class JobResponseDTO extends JobEntity {
    public JobResponseDTO(){}

    public JobResponseDTO(JobEntity entity) {
        setId(entity.getId());
        setName(entity.getName());
        setDescription(entity.getDescription());
        setPriority(entity.getPriority());
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
}
