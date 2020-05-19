package com.jobtrail.api.repositories.implementations.rowMappings;

import com.jobtrail.api.core.enums.Priority;
import com.jobtrail.api.models.entities.JobEntity;
import com.jobtrail.api.models.entities.UserEntity;
import com.jobtrail.api.models.entities.ZoneEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

public class RowMappings {
    public static UserEntity userRowMapping(ResultSet rs, int rowNum) throws SQLException {
        UserEntity user = new UserEntity();
        user.setId(rs.getObject("id", UUID.class));
        user.setUsername(rs.getString("username"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmailAddress(rs.getString("email_address"));
        user.setPassword(rs.getString("password"));
        user.setStringRoles((String[])rs.getArray("roles").getArray());
        user.setDateCreated(rs.getObject("date_created", LocalDateTime.class));
        user.setDateModified(rs.getObject("date_modified", LocalDateTime.class));
        user.setIsActive(rs.getBoolean("is_active"));
        user.setManagerId(rs.getObject("manager_id", UUID.class));
        return user;
    }

    public static JobEntity jobRowMapping(ResultSet rs, int rowNum) throws SQLException {
        JobEntity job = new JobEntity();
        job.setId(rs.getObject("id", UUID.class));
        job.setName(rs.getString("name"));
        job.setDescription(rs.getString("description"));
        job.setDueDate(rs.getObject("due_date", LocalDateTime.class));
        job.setZoneId(rs.getObject("zone_id", UUID.class));
        job.setAssignedUserId(rs.getObject("assigned_user_id", UUID.class));
        job.setPriority(rs.getObject("priority", Priority.class));
        job.setDateCreated(rs.getObject("date_created", LocalDateTime.class));
        job.setDateModified(rs.getObject("date_modified", LocalDateTime.class));
        job.setIsActive(rs.getBoolean("is_active"));
        job.setLinkId(rs.getObject("link_id", UUID.class));

        return job;
    }

    public static ZoneEntity zoneRowMapping(ResultSet rs, int rowNum) throws SQLException {
        ZoneEntity zone = new ZoneEntity();
        zone.setId(rs.getObject("id", UUID.class));
        zone.setName(rs.getString("name"));
        zone.setDescription(rs.getString("description"));
        zone.setDateCreated(rs.getObject("date_created", LocalDateTime.class));
        zone.setDateModified(rs.getObject("date_modified", LocalDateTime.class));
        zone.setIsActive(rs.getBoolean("is_active"));
        zone.setManagerId(rs.getObject("manager_id", UUID.class));

        return zone;
    }
}