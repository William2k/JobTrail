package com.jobtrail.api.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterUser {
    @NotNull(message = "Username cannot be null")
    @Size(min = 3, max = 25, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotNull(message = "FirstName cannot be null")
    @Size(min = 3, max = 25, message = "FirstName must be between 3 and 25 characters")
    private String firstName;

    @NotNull(message = "LastName cannot be null")
    @Size(min = 3, max = 25, message = "LastName must be between 3 and 25 characters")
    private String lastName;

    @NotNull(message = "Email address cannot be null")
    @Size(min = 3, max = 25, message = "LastName must be between 3 and 40 characters")
    @Email(message = "Email address must be valid")
    private String emailAddress;

    @NotNull(message = "Password cannot be null")
    @Size(min = 3, max = 25, message = "Password must be at least 8 characters")
    private String password;

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
}