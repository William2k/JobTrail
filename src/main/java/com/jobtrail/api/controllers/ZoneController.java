package com.jobtrail.api.controllers;

import com.jobtrail.api.dto.full.FullZoneResponseDTO;
import com.jobtrail.api.models.AddZone;
import com.jobtrail.api.services.ZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/zones")
public class ZoneController {
    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @RequestMapping(value = "{zoneId}", method = RequestMethod.GET)
    public Object getZone(@PathVariable("zoneId") UUID zoneId, @RequestParam( value = "full", defaultValue = "false") boolean full) {
        return full ? zoneService.getFullZone(zoneId) : zoneService.getZone(zoneId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Object getZones(@RequestParam("userId") UUID userId, @RequestParam( value = "full", defaultValue = "false") boolean full) {
        return full ? zoneService.getAllFullZonesForUser(userId) : zoneService.getAllZonesForUser(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public UUID addZone(@RequestBody AddZone model) {
        return zoneService.add(model);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteZone(@PathVariable("id") UUID zoneId) {
        zoneService.delete(zoneId);
    }
}
