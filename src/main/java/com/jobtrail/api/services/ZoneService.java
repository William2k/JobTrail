package com.jobtrail.api.services;

import com.jobtrail.api.repositories.ZoneRepository;

public class ZoneService {
    private final ZoneRepository zoneRepository;

    public ZoneService(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }
}
