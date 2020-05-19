package com.jobtrail.api.repositories.implementations;

import com.jobtrail.api.core.CustomJdbc;
import com.jobtrail.api.models.entities.JobEntity;
import com.jobtrail.api.repositories.JobRepository;
import com.jobtrail.api.repositories.implementations.rowMappings.RowMappings;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class JobRepositoryImpl implements JobRepository {
    private final CustomJdbc customJdbc;

    public JobRepositoryImpl(CustomJdbc customJdbc) {
        this.customJdbc = customJdbc;
    }

    @Override
    public List<JobEntity> getAll() {
        String sql = "SELECT * FROM job_trail.jobs";

        List<JobEntity> result = customJdbc.query(sql, RowMappings::jobRowMapping);

        return result;
    }

    @Override
    public JobEntity getById(UUID id) {
        String sql = "SELECT * FROM job_trail.jobs " +
                "WHERE id = :id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);

        JobEntity result = customJdbc.queryForObject(sql, namedParameters, RowMappings::jobRowMapping);

        return result;
    }

    @Override
    public UUID add(JobEntity job) throws SQLException {
        String sql = "INSERT INTO job_trail.jobs(name, description, due_date, is_active, link_id, zone_id, priority, assigned_user_id) " +
                "VALUES (:name, :description, :dueDate, :isActive, :linkId, :zoneId, :priority, :assignedUserId)";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("name", job.getName())
                .addValue("description", job.getDescription())
                .addValue("dueDate", job.getDueDate())
                .addValue("isActive", job.getIsActive())
                .addValue("linkId", job.getLinkId())
                .addValue("zoneId", job.getZoneId())
                .addValue("priority", job.getPriority())
                .addValue("assignedUserId", job.getAssignedUserId());

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        customJdbc.update(sql, namedParameters, keyHolder, new String[] { "id" });

        try {
            return (UUID)keyHolder.getKeys().get("id");
        } catch (Exception ex) {
            throw new SQLException("Something went wrong while adding the entity");
        }
    }

    @Override
    public void update(JobEntity job) {
        String sql = "UPDATE job_trail.jobs " +
                "SET name=:name, description=:description, due_date=:dueDate, link_id=:linkId, zone_id=:zoneId, priority=:priority, assigned_user_id=:assignedUserId, date_modified=now() " +
                "WHERE id = :id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", job.getId())
                .addValue("name", job.getName())
                .addValue("description", job.getDescription())
                .addValue("due_date", job.getDueDate())
                .addValue("link_id", job.getLinkId())
                .addValue("zone_id", job.getZoneId())
                .addValue("priority", job.getPriority())
                .addValue("assigned_user_id", job.getAssignedUserId());

        customJdbc.update(sql, namedParameters);
    }

    @Override
    public void delete(UUID id) {
        String sql = "UPDATE job_trail.jobs " +
                "SET is_active = false " +
                "WHERE id = :id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);

        customJdbc.update(sql, namedParameters);
    }
}
