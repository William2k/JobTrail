package com.jobtrail.api.controllers;

import com.jobtrail.api.models.AddJob;
import com.jobtrail.api.security.helpers.CurrentUser;
import com.jobtrail.api.services.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public Object getJobsForUser(@RequestParam("userId") UUID userId, @RequestParam(value = "full", defaultValue = "false") boolean full) {
        return full ? jobService.getFullJobsForUser(userId) : jobService.getJobsForUser(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public UUID addJob(@RequestBody AddJob addJob) {
        return jobService.add(addJob);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteZone(@PathVariable("id") UUID zoneId) {
        jobService.delete(zoneId);
    }
}
