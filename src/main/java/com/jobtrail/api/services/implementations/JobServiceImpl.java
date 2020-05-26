package com.jobtrail.api.services.implementations;

import com.jobtrail.api.core.exceptions.CustomHttpException;
import com.jobtrail.api.dto.full.FullJobResponseDTO;
import com.jobtrail.api.models.AddJob;
import com.jobtrail.api.models.entities.JobEntity;
import com.jobtrail.api.repositories.JobRepository;
import com.jobtrail.api.security.helpers.CurrentUser;
import com.jobtrail.api.services.JobService;
import com.jobtrail.api.services.UserService;
import com.jobtrail.api.services.ZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    private FullJobResponseDTO entityToDto(JobEntity entity) {
        FullJobResponseDTO job = new FullJobResponseDTO(entity);

        if(entity.getAssignedUserId() != null) {
            job.setAssignedUser(userService.getUserById(entity.getAssignedUserId()));
        }

        if(entity.getParentJobId() != null) {
            job.setParentJob(getFullJob(entity.getParentJobId()));
        }

        job.setZone(zoneService.getFullZone(entity.getZoneId()));
        job.setManager(userService.getUserById(entity.getManagerId()));

        return job;
    }

    @Override
    public JobEntity getJob(UUID id) {
        return jobRepository.getById(id);
    }

    @Override
    public FullJobResponseDTO getFullJob(UUID id) {
        JobEntity entity = jobRepository.getById(id);

        return entityToDto(entity);
    }

    @Override
    public List<JobEntity> getJobsForUser(UUID userId) {
        return jobRepository.getJobsForUser(userId);
    }

    @Override
    public List<FullJobResponseDTO> getFullJobsForUser(UUID userId) {
        List<JobEntity> jobEntities = jobRepository.getJobsForUser(userId);

        List<FullJobResponseDTO> jobs = jobEntities.parallelStream().map(this::entityToDto).collect(Collectors.toList());

        return jobs;
    }

    @Override
    public UUID add(AddJob job) {
        JobEntity entity = jobRepository.getJobByName(job.getName(), job.getZoneId());

        if(entity != null && entity.isActive()) {
            throw new CustomHttpException("A job with this name already exists", HttpStatus.CONFLICT);
        }

        entity = job.toEntity();
        entity.setActive(true);

        try {
            return jobRepository.add(entity);
        } catch (Exception ex) {
            throw new CustomHttpException("Adding job failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            JobEntity job = getJob(id);

            if(job.getManagerId() != CurrentUser.getId()) {
                throw new CustomHttpException("Not permitted to delete", HttpStatus.UNAUTHORIZED);
            }

            jobRepository.delete(id);
        } catch (Exception ex) {
            throw new CustomHttpException("deleting job failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
