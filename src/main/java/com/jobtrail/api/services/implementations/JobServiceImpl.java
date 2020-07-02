package com.jobtrail.api.services.implementations;

import com.jobtrail.api.core.exceptions.CustomHttpException;
import com.jobtrail.api.core.helpers.ConversionHelper;
import com.jobtrail.api.core.helpers.ValidationHelper;
import com.jobtrail.api.dto.full.FullJobResponseDTO;
import com.jobtrail.api.models.AddJob;
import com.jobtrail.api.models.ValidationResult;
import com.jobtrail.api.models.entities.JobEntity;
import com.jobtrail.api.repositories.JobRepository;
import com.jobtrail.api.security.helpers.CurrentUser;
import com.jobtrail.api.services.JobService;
import com.jobtrail.api.services.UserService;
import com.jobtrail.api.services.ZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;

    private final ZoneService zoneService;
    private final UserService userService;
    private final Validator validator;

    public JobServiceImpl(JobRepository jobRepository, ZoneService zoneService, UserService userService, Validator validator) {
        this.jobRepository = jobRepository;
        this.zoneService = zoneService;
        this.userService = userService;
        this.validator = validator;
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
    public List<JobEntity> getJobsForZone(UUID zoneId) {
        List<JobEntity> jobEntities = jobRepository.getJobsForZone(zoneId);

        return jobEntities;
    }

    @Override
    public JobEntity getJob(UUID id) {
        return jobRepository.getById(id);
    }

    @Override
    public List<JobEntity> getJobs(UUID zoneId, UUID userId, LocalDateTime from, LocalDateTime to) {
        List<JobEntity> jobEntities = jobRepository.getJobs(zoneId, userId, from, to);

        return jobEntities;
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
        ValidationResult result = ValidationHelper.validate(job, validator);

        if(!result.isValid()) {
            throw new CustomHttpException("Job is not valid: " + ConversionHelper.listToString(result.getErrorMessages(), ","), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        boolean exists = jobRepository.exists(job.getName(), job.getZoneId());

        if(exists) {
            throw new CustomHttpException("A job with this name already exists", HttpStatus.CONFLICT);
        }

        JobEntity entity = job.toEntity();
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
