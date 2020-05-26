package com.jobtrail.api.services;

import com.jobtrail.api.dto.full.FullJobResponseDTO;
import com.jobtrail.api.models.AddJob;
import com.jobtrail.api.models.entities.JobEntity;

import java.util.List;
import java.util.UUID;

public interface JobService {
    JobEntity getJob(UUID id);
    FullJobResponseDTO getFullJob(UUID id);
    List<JobEntity> getJobsForUser(UUID userId);
    List<FullJobResponseDTO> getFullJobsForUser(UUID userId);
    UUID add(AddJob job);
    void delete(UUID id);
}
