package com.jobtrail.api.controllers;

import com.jobtrail.api.core.exceptions.CustomHttpException;
import com.jobtrail.api.dto.UserResponseDTO;
import com.jobtrail.api.dto.UserResponseWithTokenDTO;
import com.jobtrail.api.models.RegisterUser;
import com.jobtrail.api.models.SignIn;
import com.jobtrail.api.services.AccountService;
import com.jobtrail.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    @Autowired
    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @RequestMapping(value = "authenticate", method = RequestMethod.GET)
    public UserResponseDTO authenticate(Authentication authentication) {
        if(authentication == null) {
            throw new CustomHttpException("Something went wrong with authentication", HttpStatus.BAD_REQUEST);
        }

        String username = authentication.getName();

        UserResponseDTO user = userService.getUserByUsername(username);

        return user;
    }

    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public UserResponseWithTokenDTO signIn(@RequestBody SignIn model) {
        UserResponseWithTokenDTO user = accountService.signIn(model);

        return user;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public void signUp(@RequestBody RegisterUser model) {
        accountService.signUp(model);
    }
}
