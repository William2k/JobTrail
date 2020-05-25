package com.jobtrail.api.services;

import com.jobtrail.api.dto.full.FullUserResponseWithTokenDTO;
import com.jobtrail.api.models.RegisterUser;
import com.jobtrail.api.models.SignIn;
import com.jobtrail.api.models.User;

public interface AccountService {
    FullUserResponseWithTokenDTO signIn(SignIn model);

    FullUserResponseWithTokenDTO signIn(String username, String password);

    String signUp(RegisterUser registerUser);

    String signUp(User user);

    boolean existsByUsername(String username);
}
