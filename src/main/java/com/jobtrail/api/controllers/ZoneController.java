package com.jobtrail.api.controllers;

import com.jobtrail.api.dto.ZoneResponseDTO;
import com.jobtrail.api.services.ZoneService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/zone")
public class ZoneController {
    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @RequestMapping(value = "{zoneId}", method = RequestMethod.GET)
    public ZoneResponseDTO getZone(@PathVariable("zoneId") UUID zoneId) {
        ZoneResponseDTO zone = zoneService.getById(zoneId);

        return zone;
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ZoneResponseDTO getZone(@RequestParam("name") String zoneName) {
        ZoneResponseDTO zone = zoneService.getByName(zoneName);

        return zone;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ZoneResponseDTO> getZones(@RequestParam("userId") UUID userId) {
        List<ZoneResponseDTO> zones = zoneService.getAllForUser(userId);

        return zones;
    }
}
