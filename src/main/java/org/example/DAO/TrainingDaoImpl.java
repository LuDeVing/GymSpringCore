package org.example.DAO;

import org.example.model.Trainee;
import org.example.model.Training;
import org.example.storage.StorageSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Predicate;

@Repository
public class TrainingDaoImpl implements GenericDao<Training> {

    @Autowired
    public TrainingDaoImpl(StorageSystem<Training> trainings) {
        this.trainings = trainings;
    }

    @Override
    public void create(Training training) {
        trainings.put(training.getTrainingId(), training);
    }

    @Override
    public Optional<Training> select(Long id) {
        return trainings.findById(id);
    }

    @Override
    public void update(Training entity) {
        trainings.update(entity.getTrainingId(), entity);
    }

    @Override
    public void delete(Long id) {
        trainings.delete(id);
    }

    @Override
    public boolean existsMatching(Predicate<Training> predicate) {
        return trainings.existsMatching(predicate);
    }

    private final StorageSystem<Training> trainings;

}
