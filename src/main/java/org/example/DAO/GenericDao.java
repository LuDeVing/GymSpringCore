package org.example.DAO;

import java.util.Optional;
import java.util.function.Predicate;

public interface GenericDao<T> {
    void create(T entity);
    Optional<T> select(Long id);
    void update(T entity);
    void delete(Long id);
    boolean existsMatching(Predicate<T> predicate);

}
