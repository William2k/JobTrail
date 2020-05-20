package com.jobtrail.api.services;

import com.jobtrail.api.dto.UserResponseWithTokenDTO;
import com.jobtrail.api.models.RegisterUser;
import com.jobtrail.api.models.SignIn;
import com.jobtrail.api.models.User;

public interface AccountService {
    UserResponseWithTokenDTO signIn(SignIn model);

    UserResponseWithTokenDTO signIn(String username, String password);

    String signUp(RegisterUser registerUser);

    String signUp(User user);

    boolean existsByUsername(String username);
}
