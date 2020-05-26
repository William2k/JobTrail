package com.jobtrail.api.repositories;

import com.jobtrail.api.models.entities.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends BaseRepository<UserEntity> {
    UserEntity getByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<UserEntity> getAllByZone(UUID zoneId);
}
