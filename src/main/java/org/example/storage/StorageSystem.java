package org.example.storage;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface StorageSystem <T> {

    Optional<T> findById(Long id);
    void put(Long id, T entity);
    void delete(Long id);
    void update(Long id, T entity);
    boolean existsMatching(Predicate<T> predicate);

}
