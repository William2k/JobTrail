package com.jobtrail.api.services.implementations;

import com.jobtrail.api.core.exceptions.CustomHttpException;
import com.jobtrail.api.dto.JobResponseDTO;
import com.jobtrail.api.models.AddJob;
import com.jobtrail.api.models.entities.JobEntity;
import com.jobtrail.api.repositories.JobRepository;
import com.jobtrail.api.services.JobService;
import com.jobtrail.api.services.UserService;
import com.jobtrail.api.services.ZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;

    private final ZoneService zoneService;
    private final UserService userService;

    public JobServiceImpl(JobRepository jobRepository, ZoneService zoneService, UserService userService) {
        this.jobRepository = jobRepository;
        this.zoneService = zoneService;
        this.userService = userService;
    }

    private JobResponseDTO entityToDto(JobEntity entity) {
        JobResponseDTO job = new JobResponseDTO(entity);

        job.setAssignedUser(userService.getUserById(entity.getAssignedUserId()));
        job.setParentJob(getFullJob(entity.getParentJobId()));
        job.setZone(zoneService.getFullZone(entity.getZoneId()));

        return job;
    }

    @Override
    public JobEntity getJob(String name) {
        return jobRepository.getJobByName(name);
    }

    @Override
    public JobResponseDTO getFullJob(String name) {
        JobEntity entity = jobRepository.getJobByName(name);

        return entityToDto(entity);
    }

    @Override
    public JobEntity getJob(UUID id) {
        return jobRepository.getById(id);
    }

    @Override
    public JobResponseDTO getFullJob(UUID id) {
        JobEntity entity = jobRepository.getById(id);

        return entityToDto(entity);
    }

    @Override
    public List<JobEntity> getJobs(UUID userId) {
        return jobRepository.getJobsForUser(userId);
    }

    @Override
    public List<JobResponseDTO> getFullJobs(UUID userId) {
        List<JobEntity> jobEntities = jobRepository.getJobsForUser(userId);

        List<JobResponseDTO> jobs = jobEntities.parallelStream().map(this::entityToDto).collect(Collectors.toList());

        return jobs;
    }

    @Override
    public void add(AddJob job) {
        try {
            JobEntity entity = job.toEntity();
            entity.setActive(true);

            jobRepository.add(entity);
        } catch (Exception ex) {
            throw new CustomHttpException("Adding job failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            jobRepository.delete(id);
        } catch (Exception ex) {
            throw new CustomHttpException("deleting job failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
