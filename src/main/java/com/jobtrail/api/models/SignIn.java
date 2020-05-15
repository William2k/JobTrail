package com.jobtrail.api.models;

public class SignIn {
    private String username;
    private String password;
    private boolean rememberMe;

    public String getUsername() {
        return username;
    }

    public void setUsername(String value) {
        username = value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        password = value;
    }

    public boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean value) {
        rememberMe = value;
    }
}
