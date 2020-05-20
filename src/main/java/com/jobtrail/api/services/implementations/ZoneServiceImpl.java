package com.jobtrail.api.services.implementations;

import com.jobtrail.api.repositories.ZoneRepository;
import com.jobtrail.api.services.ZoneService;

public class ZoneServiceImpl implements ZoneService {
    private final ZoneRepository zoneRepository;

    public ZoneServiceImpl(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }
}
