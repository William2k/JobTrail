package com.jobtrail.api.models.entities;


import java.time.LocalDateTime;
import java.util.UUID;

public class UserEntity extends BaseEntity {
    private String username;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private String[] stringRoles;
    private UUID managerId;

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

    public String getPassword() {return  password;}
    public void setPassword(String value) {password = value;}

    public String[] getStringRoles() {return stringRoles;}
    public void setStringRoles(String[] value) {stringRoles = value;}

    public UUID getManagerId() {
        return managerId;
    }
    public void setManagerId(UUID value) {managerId = value;}
}