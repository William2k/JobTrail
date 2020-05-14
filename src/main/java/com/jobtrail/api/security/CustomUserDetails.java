package com.jobtrail.api.security;

import com.jobtrail.api.repositories.UserRepository;
import com.jobtrail.api.models.User;
import com.jobtrail.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetails implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        final User user = new User(userRepository.getByUsername(username));

        return buildUserDetails(user);
    }

    public UserDetails loadUserById(long id) {
        final User user = new User(userRepository.getById(id));

        return buildUserDetails(user);
    }

    private UserDetails buildUserDetails(User user) throws UsernameNotFoundException {
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}