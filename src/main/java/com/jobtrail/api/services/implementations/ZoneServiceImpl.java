package com.jobtrail.api.services.implementations;

import com.jobtrail.api.core.exceptions.CustomHttpException;
import com.jobtrail.api.dto.full.FullZoneResponseDTO;
import com.jobtrail.api.models.AddZone;
import com.jobtrail.api.models.entities.ZoneEntity;
import com.jobtrail.api.dto.ZoneResponseDTO;
import com.jobtrail.api.repositories.ZoneRepository;
import com.jobtrail.api.security.helpers.CurrentUser;
import com.jobtrail.api.services.UserService;
import com.jobtrail.api.services.ZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ZoneServiceImpl implements ZoneService {
    private final ZoneRepository zoneRepository;
    private final UserService userService;

    public ZoneServiceImpl(ZoneRepository zoneRepository, UserService userService) {
        this.zoneRepository = zoneRepository;
        this.userService = userService;
    }

    private FullZoneResponseDTO entityToDto(ZoneEntity entity) {
        FullZoneResponseDTO zone = new FullZoneResponseDTO(entity);

        zone.setManager(userService.getUserById(entity.getManagerId()));

        if(entity.getParentZoneId() != null) {
            zone.setParentZone(getFullZone(entity.getParentZoneId()));
        }

        return zone;
    }

    @Override
    public ZoneResponseDTO getZone(UUID id) {
        return new ZoneResponseDTO(zoneRepository.getById(id));
    }

    @Override
    public FullZoneResponseDTO getFullZone(UUID id) {
        ZoneEntity zoneEntity = zoneRepository.getById(id);

        return entityToDto(zoneEntity);
    }

    @Override
    public List<ZoneEntity> getAllZonesForUser(UUID userId) {
        return zoneRepository.getAll(userId);
    }

    @Override
    public List<FullZoneResponseDTO> getAllFullZonesForUser(UUID userId) {
        List<ZoneEntity> zoneEntities = zoneRepository.getAll(userId);

        List<FullZoneResponseDTO> zones = zoneEntities.parallelStream().map(this::entityToDto).collect(Collectors.toList());

        return zones;
    }

    @Override
    public UUID add(AddZone zone) {
        ZoneEntity entity = zoneRepository.getByName(zone.getName(), zone.getParentZoneId());

        if(entity != null && entity.isActive()) {
            throw new CustomHttpException("A zone with this name already exists under the provided parent zone", HttpStatus.CONFLICT);
        }

        entity = zone.toEntity();
        entity.setActive(true);

        try {
            return zoneRepository.add(entity);
        } catch (Exception ex) {
            throw new CustomHttpException("Adding zone failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            ZoneEntity zone = zoneRepository.getById(id);

            if(zone.getManagerId() != CurrentUser.getId()) {
                throw new CustomHttpException("Not permitted to delete", HttpStatus.UNAUTHORIZED);
            }

            zoneRepository.delete(id);
        } catch (Exception ex) {
            throw new CustomHttpException("deleting zone failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
