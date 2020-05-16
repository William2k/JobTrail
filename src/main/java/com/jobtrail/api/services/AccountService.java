package com.jobtrail.api.services;

import com.jobtrail.api.core.exceptions.CustomHttpException;
import com.jobtrail.api.dto.UserResponseWithTokenDTO;
import com.jobtrail.api.models.RegisterUser;
import com.jobtrail.api.models.Role;
import com.jobtrail.api.models.SignIn;
import com.jobtrail.api.models.User;
import com.jobtrail.api.models.entities.UserEntity;
import com.jobtrail.api.repositories.UserRepository;
import com.jobtrail.api.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AccountService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public UserResponseWithTokenDTO signIn(SignIn model) {
        return signIn(model.getUsername(), model.getPassword());
    }

    public UserResponseWithTokenDTO signIn(String username, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            authenticationManager.authenticate(authenticationToken);

            UserEntity userEntity = userRepository.getByUsername(username);

            User user = new User(userRepository.getByUsername(username));

            String token = jwtTokenProvider.createToken(username, user.getId(), user.getRoles());

            return new UserResponseWithTokenDTO(userEntity, token);
        } catch (AuthenticationException e) {
            throw new CustomHttpException("Invalid username/password supplied", HttpStatus.UNAUTHORIZED);
        }
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