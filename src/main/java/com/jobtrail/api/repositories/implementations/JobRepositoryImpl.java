package com.jobtrail.api.repositories.implementations;

import com.jobtrail.api.core.CustomJdbc;
import com.jobtrail.api.models.entities.JobEntity;
import com.jobtrail.api.repositories.JobRepository;
import com.jobtrail.api.repositories.implementations.rowMappings.RowMappings;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class JobRepositoryImpl implements JobRepository {
    private final CustomJdbc customJdbc;

    public JobRepositoryImpl(CustomJdbc customJdbc) {
        this.customJdbc = customJdbc;
    }

    @Override
    public List<JobEntity> getAll() {
        String sql = "SELECT * FROM job_trail.jobs " +
                "ORDER BY due_date";

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
    public boolean exists(String name, UUID zoneId) {
        String sql = "SELECT EXISTS(SELECT * FROM job_trail.jobs " +
                "WHERE name = :name AND zone_id = :zoneId)";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("zoneId", zoneId);

        boolean result = customJdbc.queryForObject(sql, namedParameters, boolean.class);

        return result;
    }

    @Override
    public List<JobEntity> getJobs(UUID zoneId, UUID userId, LocalDateTime from, LocalDateTime to) {
        String sql = "SELECT * FROM job_trail.jobs " +
                "WHERE is_active = true ";

        if(zoneId != null) {
            sql += "AND zone_id = :zoneId ";
        }

        if(userId != null) {
            sql += "AND assigned_user_id = :userId ";
        }

        if(from != null && to != null) {
            sql += "AND due_date >= :from AND due_date <= :to ";
        }

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("zoneId", zoneId)
                .addValue("userId", userId)
                .addValue("from", from)
                .addValue("to", to);

        List<JobEntity> result = customJdbc.query(sql, namedParameters, RowMappings::jobRowMapping);

        return result;
    }

    @Override
    public List<JobEntity> getJobsForZone(UUID zoneId) {
        String sql = "SELECT * FROM job_trail.jobs " +
                "WHERE zone_id = :zoneId " +
                "AND parent_job_id IS NULL";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("zoneId", zoneId);

        List<JobEntity> result = customJdbc.query(sql, namedParameters, RowMappings::jobRowMapping);

        return result;
    }

    @Override
    public JobEntity getJobByName(String name, UUID zoneId) {
        String sql = "SELECT * FROM job_trail.jobs " +
                "WHERE name = :name AND zone_id = :zoneId";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("zoneId", zoneId);

        JobEntity result = customJdbc.queryForObject(sql, namedParameters, RowMappings::jobRowMapping);

        return result;
    }

    @Override
    public List<JobEntity> getJobsForUser(UUID userId) {
        String sql = "SELECT * FROM job_trail.jobs " +
                "WHERE assigned_user_id = :userId " +
                "ORDER BY due_date";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("userId", userId);

        List<JobEntity> result = customJdbc.query(sql, namedParameters, RowMappings::jobRowMapping);

        return result;
    }

    @Override
    public List<JobEntity> getJobsForUser(String username) {
        String sql = "SELECT jobs.* FROM job_trail.jobs INNER JOIN job_trail.users ON users.id = assigned_user_id " +
                "WHERE normalised_username = :username " +
                "ORDER BY due_date";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("username", username.toUpperCase());

        List<JobEntity> result = customJdbc.query(sql, namedParameters, RowMappings::jobRowMapping);

        return result;
    }

    @Override
    public UUID add(JobEntity job) throws SQLException {
        String sql = "INSERT INTO job_trail.jobs(name, description, due_date, is_active, is_recurring, parent_job_id, zone_id, priority, assigned_user_id, manager_id) " +
                "VALUES (:name, :description, :dueDate, :isActive, :recurring, :parentJobId, :zoneId, :priority, :assignedUserId, :managerId)";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("name", job.getName())
                .addValue("description", job.getDescription())
                .addValue("dueDate", job.getDueDate())
                .addValue("isActive", job.isActive())
                .addValue("recurring", job.isRecurring())
                .addValue("parentJobId", job.getParentJobId())
                .addValue("zoneId", job.getZoneId())
                .addValue("priority", job.getPriority().name())
                .addValue("assignedUserId", job.getAssignedUserId())
                .addValue("managerId", job.getManagerId());

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
                "SET name=:name, description=:description, due_date=:dueDate, parent_job_id=:parentJobId, zone_id=:zoneId, is_recurring=:recurring, priority=:priority, assigned_user_id=:assignedUserId, date_modified=now() " +
                "WHERE id = :id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", job.getId())
                .addValue("name", job.getName())
                .addValue("description", job.getDescription())
                .addValue("dueDate", job.getDueDate())
                .addValue("recurring", job.isRecurring())
                .addValue("parentJobId", job.getParentJobId())
                .addValue("zoneId", job.getZoneId())
                .addValue("priority", job.getPriority().name())
                .addValue("assignedUserId", job.getAssignedUserId());

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
