package com.jobtrail.api.services;

import com.jobtrail.api.config.ConfigProperties;
import com.jobtrail.api.core.exceptions.CustomHttpException;
import com.jobtrail.api.models.RegisterUser;
import com.jobtrail.api.models.Role;
import com.jobtrail.api.models.User;
import com.jobtrail.api.repositories.UserRepository;
import com.jobtrail.api.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AccountService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String signUp(RegisterUser registerUser) {
        User user = new User(registerUser);
        return signUp(user);
    }

    public String signUp(User user) {
        if (!existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.addRole(Role.ROLE_USER);
            user.setIsActive(true);

            try {
                userRepository.add(user);
            } catch (Exception ex) {
                throw new CustomHttpException("Sign up failed", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return jwtTokenProvider.createToken(user.getUsername(), user.getId(), user.getRoles());
        } else {
            throw new CustomHttpException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}