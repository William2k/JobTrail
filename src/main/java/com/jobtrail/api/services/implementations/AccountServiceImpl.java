package com.jobtrail.api.services.implementations;

import com.jobtrail.api.core.exceptions.CustomHttpException;
import com.jobtrail.api.dto.UserResponseWithTokenDTO;
import com.jobtrail.api.models.RegisterUser;
import com.jobtrail.api.models.Role;
import com.jobtrail.api.models.SignIn;
import com.jobtrail.api.models.User;
import com.jobtrail.api.models.entities.UserEntity;
import com.jobtrail.api.repositories.UserRepository;
import com.jobtrail.api.security.JwtTokenProvider;
import com.jobtrail.api.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AccountServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserResponseWithTokenDTO signIn(SignIn model) {
        return signIn(model.getUsername(), model.getPassword());
    }

    @Override
    public UserResponseWithTokenDTO signIn(String username, String password) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

            authentication = authenticationManager.authenticate(authentication);

            if(!authentication.isAuthenticated()) { // Sanity check, not needed as authenticate method will crash anyway if they are not authenticated.
                throw new CustomHttpException("Invalid username/password supplied", HttpStatus.UNAUTHORIZED);
            }

            UserEntity userEntity = userRepository.getByUsername(username);

            User user = new User(userEntity);

            String token = jwtTokenProvider.createToken(username, user.getId(), user.getRoles());

            return new UserResponseWithTokenDTO(userEntity, token);
        } catch (AuthenticationException e) {
            throw new CustomHttpException("Invalid username/password supplied", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public String signUp(RegisterUser registerUser) {
        User user = new User(registerUser);
        return signUp(user);
    }

    @Override
    public String signUp(User user) {
        if (!existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.addRole(Role.ROLE_USER);
            user.setActive(true);

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

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}