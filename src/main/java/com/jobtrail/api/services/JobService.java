package com.jobtrail.api.services;

import com.jobtrail.api.dto.JobResponseDTO;
import com.jobtrail.api.models.AddJob;
import com.jobtrail.api.models.entities.JobEntity;

import java.util.List;
import java.util.UUID;

public interface JobService {
    JobEntity getJob(String name);
    JobResponseDTO getFullJob(String name);
    JobEntity getJob(UUID id);
    JobResponseDTO getFullJob(UUID id);
    List<JobEntity> getJobs(UUID userId);
    List<JobResponseDTO> getFullJobs(UUID userId);
    void add(AddJob job);
    void delete(UUID id);
}
