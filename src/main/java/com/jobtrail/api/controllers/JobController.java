package com.jobtrail.api.controllers;

import com.jobtrail.api.core.exceptions.CustomHttpException;
import com.jobtrail.api.models.AddJob;
import com.jobtrail.api.models.entities.JobEntity;
import com.jobtrail.api.security.helpers.CurrentUser;
import com.jobtrail.api.services.JobService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Object getJobs(@PathVariable("id") UUID  jobId, @RequestParam(value = "full", defaultValue = "false") boolean full) {
        return full ? jobService.getFullJob(jobId) : jobService.getJob(jobId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<JobEntity> getJobs(
            @RequestParam(value = "zoneId", defaultValue = "") UUID zoneId, @RequestParam(value = "userId", defaultValue = "") UUID userId,
            @RequestParam(value = "from", defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(value = "to", defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return jobService.getJobs(zoneId, userId, from, to);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public UUID addJob(@RequestBody AddJob addJob) {
        return jobService.add(addJob);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteJob(@PathVariable("id") UUID jobId) {
        jobService.delete(jobId);
    }
}
