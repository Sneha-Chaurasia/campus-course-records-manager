package edu.ccrm.service;

import java.io.IOException;
import java.util.List;

public interface Persistable<T> {
    void save(T entity) throws IOException;
    void saveAll(List<T> entities) throws IOException;
    T findById(String id);
    List<T> findAll();
    void delete(String id);
    boolean exists(String id);
}
