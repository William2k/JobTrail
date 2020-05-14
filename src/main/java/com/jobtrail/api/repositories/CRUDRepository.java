package com.jobtrail.api.repositories;

import java.sql.SQLException;
import java.util.List;

public interface CRUDRepository<T> {
    List<T> getAll();
    T getById(long id);
    long add(T entity) throws SQLException;
    void update(T entity);
    void delete(long id);
}
