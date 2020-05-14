package com.jobtrail.api.repositories;

import com.jobtrail.api.models.entities.UserEntity;

public interface UserRepository extends CRUDRepository<UserEntity> {
    UserEntity getByUsername(String username);

    boolean existsByUsername(String username);
}
