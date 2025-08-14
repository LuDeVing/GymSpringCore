package org.example.DAO;

import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.storage.MapStorage;
import org.example.storage.StorageSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Predicate;

@Repository
public class TrainerDaoImpl implements GenericDao<Trainer> {

    @Autowired
    public TrainerDaoImpl(StorageSystem<Trainer> trainers) {
        this.trainers = trainers;
    }

    @Override
    public void create(Trainer trainer) {
        trainers.put(trainer.getUserId(), trainer);
    }

    @Override
    public Optional<Trainer> select(Long id) {
        return trainers.findById(id);
    }

    @Override
    public void update(Trainer trainer) {
        trainers.update(trainer.getUserId(), trainer);
    }

    @Override
    public void delete(Long id) {
        trainers.delete(id);
    }

    @Override
    public boolean existsMatching(Predicate<Trainer> predicate) {
        return trainers.existsMatching(predicate);
    }

    private final StorageSystem<Trainer> trainers;

}
