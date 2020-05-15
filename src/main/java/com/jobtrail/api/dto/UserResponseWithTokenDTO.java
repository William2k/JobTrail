package com.jobtrail.api.dto;

import com.jobtrail.api.models.entities.UserEntity;

public class UserResponseWithTokenDTO {
    public UserResponseWithTokenDTO() {
    }

    public UserResponseWithTokenDTO(UserEntity userEntity, String token) {
        setUser(new UserResponseDTO(userEntity));
        setToken(token);
    }

    private UserResponseDTO user;
    private String token;

    public UserResponseDTO getUser() {return user;}
    public void setUser(UserResponseDTO value) {user = value;}

    public String getToken() {
        return token;
    }
    public void setToken(String value) {token = value;}
}
