package com.jobtrail.api.config;

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
