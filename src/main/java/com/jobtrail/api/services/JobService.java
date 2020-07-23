package com.jobtrail.api.services;

import com.jobtrail.api.dto.full.FullJobResponseDTO;
import com.jobtrail.api.models.AddJob;
import com.jobtrail.api.models.entities.JobEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface JobService {
    List<JobEntity> getJobsForZone(UUID zoneId);
    JobEntity getJob(UUID id);
    List<JobEntity> getJobs(UUID zoneId, UUID userId, LocalDateTime from, LocalDateTime to);
    FullJobResponseDTO getFullJob(UUID id);
    List<JobEntity> getJobsForUser(UUID userId);
    List<FullJobResponseDTO> getFullJobsForUser(UUID userId);
    void takeJob(UUID jobId);
    UUID add(AddJob job);
    void delete(UUID id);
}
