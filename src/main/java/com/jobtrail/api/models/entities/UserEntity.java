package com.jobtrail.api.models.entities;


import java.time.LocalDateTime;

public class UserEntity {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private String[] stringRoles;
    private boolean isActive;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;

    public long getId() {
        return id;
    }
    public void setId(long value) {id = value;}

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

    public boolean getIsActive() {return  isActive;}
    public void  setIsActive(boolean value) {isActive = value;}

    public LocalDateTime getDateCreated() {return  dateCreated;}
    public void  setDateCreated(LocalDateTime value) {dateCreated = value;}

    public LocalDateTime getDateModified() {return  dateModified;}
    public void  setDateModified(LocalDateTime value) {dateModified = value;}
}