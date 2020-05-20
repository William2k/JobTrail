package com.jobtrail.api.services.implementations;

import com.jobtrail.api.services.JobService;

public class JobServiceImpl implements JobService {
    private final JobService jobService;

    public JobServiceImpl(JobService jobService) {
        this.jobService = jobService;
    }
}
