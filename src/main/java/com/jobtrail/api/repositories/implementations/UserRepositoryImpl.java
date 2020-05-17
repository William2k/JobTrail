package com.jobtrail.api.repositories.implementations;

import com.jobtrail.api.core.CustomJdbc;
import com.jobtrail.api.models.entities.UserEntity;
import com.jobtrail.api.repositories.UserRepository;
import com.jobtrail.api.repositories.implementations.rowMappings.RowMappings;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final CustomJdbc customJdbc;

    public UserRepositoryImpl(CustomJdbc customJdbc) {
        this.customJdbc = customJdbc;
    }

    @Override
    public UserEntity getByUsername(String username) {
        String sql = "SELECT * FROM job_trail.users " +
                "WHERE normalised_username = :username";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("username", username.toUpperCase());

        UserEntity result = customJdbc.queryForObject(sql, namedParameters, RowMappings::userRowMapping);

        return result;
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = "SELECT EXISTS (SELECT id FROM job_trail.users " +
                "WHERE normalised_username = :username)";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("username", username.toUpperCase());

        boolean result = customJdbc.queryForObject(sql, namedParameters, boolean.class); // ignore error postgresql will always return true or false for this

        return result;
    }

    @Override
    public List<UserEntity> getAll() {
        List<UserEntity> result = customJdbc.query("SELECT * FROM job_trail.users", RowMappings::userRowMapping);

        return result;
    }

    @Override
    public UserEntity getById(UUID id) {
        String sql = "SELECT * FROM job_trail.users " +
                "WHERE id = :id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);

        UserEntity result = customJdbc.queryForObject(sql, namedParameters, RowMappings::userRowMapping);

        return result;
    }

    @Override
    public UUID add(UserEntity user) throws SQLException {
        String sql = "INSERT INTO job_trail.users(username, normalised_username, first_name, last_name, email_address, password, roles, is_active) " +
                "VALUES (:username, :normalisedUsername, :firstName, :lastName, :emailAddress, :password, :roles, :isActive)";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("normalisedUsername", user.getUsername().toUpperCase())
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("emailAddress", user.getEmailAddress())
                .addValue("password", user.getPassword())
                .addValue("roles", user.getStringRoles())
                .addValue("isActive", user.getIsActive());

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        customJdbc.update(sql, namedParameters, keyHolder, new String[] { "id" });

        try {
            return (UUID)keyHolder.getKeys().get("id");
        } catch (Exception ex) {
            throw new SQLException("Something went wrong while adding the entity");
        }
    }

    @Override
    public void update(UserEntity user) {
        String sql = "UPDATE job_trail.users " +
                "SET first_name=:firstName, last_name=:lastName, email_address=:emailAddress, password=:password, roles=:roles, date_modified=now() " +
                "WHERE id = :id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("emailAddress", user.getEmailAddress())
                .addValue("password", user.getPassword())
                .addValue("roles", user.getStringRoles());

        customJdbc.update(sql, namedParameters);
    }

    @Override
    public void delete(long id) {
        String sql = "UPDATE job_trail.users " +
                "SET is_active = false " +
                "WHERE id = :id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);

        customJdbc.update(sql, namedParameters);
    }
}