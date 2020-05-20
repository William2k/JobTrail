package com.jobtrail.api.services;

import com.jobtrail.api.dto.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponseDTO> getUsers();

    UserResponseDTO getUserById(UUID id);

    UserResponseDTO getUserByUsername(String username);
}
