package com.jobtrail.api.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_USER, ROLE_MANAGER, ROLE_STAFF;

    public String getAuthority() {
        return name();
    }
}