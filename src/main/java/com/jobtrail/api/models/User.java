package com.jobtrail.api.models;

import com.jobtrail.api.models.entities.UserEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class User extends UserEntity {
    public User() {

    }

    public User(RegisterUser model) {
        setUsername(model.getUsername());
        setFirstName(model.getFirstName());
        setLastName(model.getLastName());
        setEmailAddress(model.getEmailAddress());
        setPassword(model.getPassword());
    }

    public User(UserEntity entity) {
        setId(entity.getId());
        setUsername(entity.getUsername());
        setPassword(entity.getPassword());
        setEmailAddress(entity.getEmailAddress());
        setFirstName(entity.getFirstName());
        setLastName(entity.getLastName());
        setActive(entity.isActive());
        List<Role> roles = Arrays.stream(entity.getStringRoles()).map(Role::valueOf).collect(Collectors.toList());
        setRoles(roles);
    }

    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> value) {
        roles = value;
        String[] roles = value.stream().map(Role::getAuthority).toArray(String[]::new);
        setStringRoles(roles);
    }

    public void addRole(Role role) {
        if(roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
        setRoles(roles);
    }
}
