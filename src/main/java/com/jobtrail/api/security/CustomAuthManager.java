package com.jobtrail.api.security;

import com.jobtrail.api.models.entities.UserEntity;
import com.jobtrail.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class CustomAuthManager implements AuthenticationManager {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        UserEntity userEntity = userRepository.getByUsername(auth.getName());

        if(userEntity == null) {
            throw new BadCredentialsException("Bad Credentials");
        }

        boolean isValid = passwordEncoder.matches(auth.getCredentials().toString(), userEntity.getPassword());

        if (isValid) {
            return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials(), AUTHORITIES);
        }

        throw new BadCredentialsException("Bad Credentials");
    }
}