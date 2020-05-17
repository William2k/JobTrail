package com.jobtrail.api.services;

import com.jobtrail.api.dto.UserResponseDTO;
import com.jobtrail.api.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> getUsers() {
        return userRepository.getAll().parallelStream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(UUID id) {
        return new UserResponseDTO(userRepository.getById(id));
    }

    public UserResponseDTO getUserByUsername(String username) {return new UserResponseDTO(userRepository.getByUsername(username));}
}
