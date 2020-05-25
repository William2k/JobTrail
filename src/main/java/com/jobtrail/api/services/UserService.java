package com.jobtrail.api.services;

import com.jobtrail.api.dto.full.FullUserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<FullUserResponseDTO> getUsers();

    FullUserResponseDTO getUserById(UUID id);

    FullUserResponseDTO getUserByUsername(String username);
}
