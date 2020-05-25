package com.jobtrail.api.dto.full;

import com.jobtrail.api.models.entities.UserEntity;

public class FullUserResponseWithTokenDTO {
    public FullUserResponseWithTokenDTO() {
    }

    public FullUserResponseWithTokenDTO(UserEntity userEntity, String token) {
        setUser(new FullUserResponseDTO(userEntity));
        setToken(token);
    }

    private FullUserResponseDTO user;
    private String token;

    public FullUserResponseDTO getUser() {return user;}
    public void setUser(FullUserResponseDTO value) {user = value;}

    public String getToken() {
        return token;
    }
    public void setToken(String value) {token = value;}
}
