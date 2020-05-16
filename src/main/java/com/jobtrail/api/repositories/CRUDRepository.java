package com.jobtrail.api.repositories;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface CRUDRepository<T> {
    List<T> getAll();
    T getById(long id);
    UUID add(T entity) throws SQLException;
    void update(T entity);
    void delete(long id);
}
