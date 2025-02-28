package com.jobtrail.api.repositories.implementations;

import com.jobtrail.api.core.CustomJdbc;
import com.jobtrail.api.models.entities.ZoneEntity;
import com.jobtrail.api.repositories.ZoneRepository;
import com.jobtrail.api.repositories.implementations.rowMappings.RowMappings;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class ZoneRepositoryImpl implements ZoneRepository {
    private final CustomJdbc customJdbc;

    public ZoneRepositoryImpl(CustomJdbc customJdbc) {
        this.customJdbc = customJdbc;
    }

    @Override
    public List<ZoneEntity> getAll() {
        String sql = "SELECT * FROM job_trail.zones";

        List<ZoneEntity> result = customJdbc.query(sql, RowMappings::zoneRowMapping);

        return result;
    }

    @Override
    public List<ZoneEntity> getAll(UUID userId) {
        String sql = "SELECT zones.* FROM job_trail.zones JOIN job_trail.users_zones ON id = zone_id " +
                "WHERE user_id = :userId";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("userId", userId);

        List<ZoneEntity> result = customJdbc.query(sql, namedParameters, RowMappings::zoneRowMapping);

        return result;
    }

    @Override
    public List<ZoneEntity> getAll(String username) {
        String sql = "SELECT zones.* FROM job_trail.users JOIN job_trail.users_zones ON user_id = users.id INNER JOIN job_trail.zones ON zones.id = zone_id " +
                "WHERE normalised_username = :username";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("username", username.toUpperCase());

        List<ZoneEntity> result = customJdbc.query(sql, namedParameters, RowMappings::zoneRowMapping);

        return result;
    }

    @Override
    public ZoneEntity getById(UUID id) {
        String sql = "SELECT * FROM job_trail.zones " +
                "WHERE id = :id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);

        ZoneEntity result = customJdbc.queryForObject(sql, namedParameters, RowMappings::zoneRowMapping);

        return result;
    }

    @Override
    public boolean exists(String name, UUID parentZoneId) {
        String sql = "SELECT EXISTS (SELECT * FROM job_trail.zones " +
                "WHERE UPPER(name) = :name AND parent_zone_id = :parentZoneId)";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("name", name.toUpperCase())
                .addValue("parentZoneId", parentZoneId);

        boolean result = customJdbc.queryForObject(sql, namedParameters, boolean.class);

        return result;
    }

    @Override
    public ZoneEntity getByName(String name, UUID parentZoneId) {
        String sql = "SELECT * FROM job_trail.zones " +
                "WHERE UPPER(name) = :name AND parent_zone_id = :parentZoneId";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("name", name.toUpperCase())
                .addValue("parentZoneId", parentZoneId);

        ZoneEntity result = customJdbc.queryForObject(sql, namedParameters, RowMappings::zoneRowMapping);

        return result;
    }

    @Override
    public UUID add(ZoneEntity zone) throws SQLException {
        String sql = "INSERT INTO job_trail.zones(name, description, is_active, manager_id, parent_zone_id) " +
                "VALUES (:name, :description, :isActive, :managerId, :parentZoneId)";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("name", zone.getName())
                .addValue("description", zone.getDescription())
                .addValue("isActive", zone.isActive())
                .addValue("managerId", zone.getManagerId())
                .addValue("parentZoneId", zone.getParentZoneId());

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            customJdbc.update(sql, namedParameters, keyHolder, new String[] { "id" });

            UUID zoneId = (UUID)keyHolder.getKeys().get("id");

            addUserToZone(zone.getManagerId(), zoneId, "Admin");

            return zoneId;
        } catch (Exception ex) {
            throw new SQLException("Something went wrong while adding the entity");
        }
    }

    @Override
    public void addUserToZone(UUID userId, UUID zoneId, String role) {
        String sql = "INSERT INTO job_trail.users_zones(user_id, zone_id, role, is_active) " +
                "VALUES (:userId, :zoneId, :role, true)";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("zoneId", zoneId)
                .addValue("role", role);

        customJdbc.update(sql, namedParameters);
    }

    @Override
    public void update(ZoneEntity zone) {
        String sql = "UPDATE job_trail.zones " +
                "SET name=:name, description=:description, manager_id=:managerId, parent_zone_id = :parentZoneId, date_modified=now() " +
                "WHERE id = :id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", zone.getId())
                .addValue("name", zone.getName())
                .addValue("description", zone.getDescription())
                .addValue("managerId", zone.getManagerId())
                .addValue("parentZoneId", zone.getParentZoneId());

        customJdbc.update(sql, namedParameters);
    }

    @Override
    public void delete(UUID id) {
        String sql = "UPDATE job_trail.zones " +
                "SET is_active = false " +
                "WHERE id = :id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);

        customJdbc.update(sql, namedParameters);
    }
}
