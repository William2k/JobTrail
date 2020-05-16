package com.jobtrail.api.services;

import com.jobtrail.api.models.RegisterUser;
import com.jobtrail.api.models.User;
import com.jobtrail.api.repositories.UserRepository;
import com.jobtrail.api.security.JwtTokenProvider;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void userSignUp() {
        RegisterUser registerUser = new RegisterUser();
        registerUser.setFirstName("First");
        registerUser.setLastName("Last");
        registerUser.setEmailAddress("FIrst@email.com");
        registerUser.setUsername("User");
        registerUser.setPassword("Pass");

        String token = accountService.signUp(registerUser);

        Assert.assertNotNull("Auth token is null", token);
        Assert.assertNotEquals("Token is empty", "",token);
    }
}
