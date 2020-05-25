package com.jobtrail.api.controllers;

import com.jobtrail.api.core.exceptions.CustomHttpException;
import com.jobtrail.api.dto.full.FullUserResponseDTO;
import com.jobtrail.api.dto.full.FullUserResponseWithTokenDTO;
import com.jobtrail.api.models.RegisterUser;
import com.jobtrail.api.models.SignIn;
import com.jobtrail.api.services.AccountService;
import com.jobtrail.api.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @RequestMapping(value = "authenticate", method = RequestMethod.GET)
    public FullUserResponseDTO authenticate(Authentication authentication) {
        if(authentication == null) {
            throw new CustomHttpException("Authentication failed", HttpStatus.UNAUTHORIZED);
        }

        String username = authentication.getName();
        FullUserResponseDTO user = userService.getUserByUsername(username);

        return user;
    }

    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public FullUserResponseWithTokenDTO signIn(@RequestBody SignIn model) {
        FullUserResponseWithTokenDTO user = accountService.signIn(model);

        return user;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public void signUp(@RequestBody RegisterUser model) {
        accountService.signUp(model);
    }
}
