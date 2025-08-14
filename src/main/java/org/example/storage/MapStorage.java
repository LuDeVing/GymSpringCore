package org.example.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

@Component
public class MapStorage<T> implements StorageSystem<T> {

    public Optional<T> findById(Long id) {
        T value = map.get(id);
        if (value != null) {
            return Optional.of(value);
        } else {
            logger.warn("Entity with ID {} does not exist in the map!", id);
            return Optional.empty();
        }
    }

    public void put(Long id, T entity) {
        T previous = map.putIfAbsent(id, entity);
        String type = entity.getClass().getSimpleName();
        if (previous == null) {
            logger.info("{} with ID {} created.", type, id);
        } else {
            logger.info("{} {} is already in the map!", type, id);
        }
    }

    public void update(Long id, T entity) {
        T previous = map.replace(id, entity);
        String type = entity.getClass().getSimpleName();
        if (previous != null) {
            logger.info("{} with ID {} updated.", type, id);
        } else {
            logger.warn("{} with ID {} not found for update.", type, id);
        }
    }

    @Override
    public boolean existsMatching(Predicate<T> predicate) {
        for(T value : map.values()){
            if (predicate.test(value))
                return true;
        }
        return false;
    }

    public void delete(Long id) {
        T removed = map.remove(id);
        if (removed != null) {
            logger.info("{} with ID {} deleted.", removed.getClass().getSimpleName(), id);
        } else {
            logger.warn("Entity with ID {} not found for deletion.", id);
        }
    }



    protected final ConcurrentHashMap<Long, T> map = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(MapStorage.class);

}