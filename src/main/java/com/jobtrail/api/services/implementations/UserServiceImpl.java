package com.jobtrail.api.services.implementations;

import com.jobtrail.api.dto.full.FullUserResponseDTO;
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
    public List<FullUserResponseDTO> getUsers() {
        return userRepository.getAll().parallelStream()
                .map(FullUserResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public FullUserResponseDTO getUserById(UUID id) {
        return new FullUserResponseDTO(userRepository.getById(id));
    }

    @Override
    public FullUserResponseDTO getUserByUsername(String username) {return new FullUserResponseDTO(userRepository.getByUsername(username));}
}
