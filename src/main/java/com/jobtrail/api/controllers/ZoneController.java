package com.jobtrail.api.controllers;

import com.jobtrail.api.core.ApiResponse;
import com.jobtrail.api.dto.ZoneResponseDTO;
import com.jobtrail.api.models.AddZone;
import com.jobtrail.api.services.ZoneService;
import org.springframework.http.HttpStatus;
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
    public ApiResponse getZone(@PathVariable("zoneId") UUID zoneId, @RequestParam( value = "full", defaultValue = "false") boolean full) {
        return full ? zoneService.getFullZone(zoneId) : zoneService.getZone(zoneId);
    }



    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ZoneResponseDTO getZone(@RequestParam("name") String zoneName) {
        ZoneResponseDTO zone = zoneService.getFullZone(zoneName);

        return zone;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ZoneResponseDTO> getZones(@RequestParam("userId") UUID userId) {
        List<ZoneResponseDTO> zones = zoneService.getAllFullZonesForUser(userId);

        return zones;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void addZone(@RequestBody AddZone model) {
        zoneService.add(model);
    }
}
