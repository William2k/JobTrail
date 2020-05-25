package com.jobtrail.api.dto.full;

import com.jobtrail.api.dto.FullBaseDTO;
import com.jobtrail.api.models.entities.UserEntity;

import java.util.UUID;

public class FullUserResponseDTO extends FullBaseDTO {
    public FullUserResponseDTO() { }

    public FullUserResponseDTO(UserEntity entity) {
        setId(entity.getId());
        setUsername(entity.getUsername());
        setFirstName(entity.getFirstName());
        setLastName(entity.getLastName());
        setEmailAddress(entity.getEmailAddress());
        setRoles(entity.getStringRoles());
        setDateCreated(entity.getDateCreated());
        setDateModified(entity.getDateModified());
        setActive(entity.isActive());
    }

    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String[] stringRoles;
    private FullUserResponseDTO manager;

    public UUID getId() { return id; }
    public void setId(UUID value) {id = value;}

    public String getUsername() {
        return username;
    }
    public void setUsername(String value) {username = value;}

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String value) {firstName = value;}

    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String value) {emailAddress = value;}

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String value) {lastName = value;}

    public String[] getRoles() {return stringRoles;}
    public void setRoles(String[] value) {stringRoles = value;}

    public FullUserResponseDTO getManager() { return manager; }
    public void setManager(FullUserResponseDTO value) { manager = value; }
}
