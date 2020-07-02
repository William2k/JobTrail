package com.jobtrail.api.repositories;

import com.jobtrail.api.models.entities.JobEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface JobRepository extends BaseRepository<JobEntity> {
    boolean exists(String name, UUID zoneId);
    List<JobEntity> getJobs(UUID zoneId, UUID userId, LocalDateTime from, LocalDateTime to);
    List<JobEntity> getJobsForZone(UUID zoneId);
    JobEntity getJobByName(String name, UUID zoneId);
    List<JobEntity> getJobsForUser(UUID userId);
    List<JobEntity> getJobsForUser(String username);
}
