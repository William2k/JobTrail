package com.jobtrail.api.repositories;

import com.jobtrail.api.models.entities.JobEntity;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends BaseRepository<JobEntity> {
    boolean exists(String name, UUID zoneId);
    List<JobEntity> getJobsForZone(UUID zoneId);
    JobEntity getJobByName(String name, UUID zoneId);
    List<JobEntity> getJobsForUser(UUID userId);
    List<JobEntity> getJobsForUser(String username);
}
