package com.jobtrail.api.services.implementations;

import com.jobtrail.api.dto.UserResponseDTO;
import com.jobtrail.api.repositories.UserRepository;
import com.jobtrail.api.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponseDTO> getUsers() {
        return userRepository.getAll().parallelStream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(UUID id) {
        return new UserResponseDTO(userRepository.getById(id));
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) {return new UserResponseDTO(userRepository.getByUsername(username));}
}
