package com.jobtrail.api.config;

import com.jobtrail.api.models.entities.UserEntity;
import com.jobtrail.api.repositories.UserRepository;
import com.jobtrail.api.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class TestConfig {
    @Autowired
    private UserRepository userRepository;

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        UserDetails user = new org.springframework.security.core.userdetails.User("admin", "admin", Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER")
        ));

        return new InMemoryUserDetailsManager(Arrays.asList(
                user
        ));
    }
}
