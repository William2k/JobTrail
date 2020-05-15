package com.jobtrail.api.repositories.implementations.rowMappings;

import com.jobtrail.api.models.entities.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class RowMappings {
    public static UserEntity userRowMapping(ResultSet rs, int rowNum) throws SQLException {
        UserEntity user = new UserEntity();
        //user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmailAddress(rs.getString("email_address"));
        user.setPassword(rs.getString("password"));
        user.setStringRoles((String[])rs.getArray("roles").getArray());
        user.setDateCreated(rs.getObject("date_created", LocalDateTime.class));
        user.setDateModified(rs.getObject("date_modified", LocalDateTime.class));
        user.setIsActive(rs.getBoolean("is_active"));
        return user;
    }
}