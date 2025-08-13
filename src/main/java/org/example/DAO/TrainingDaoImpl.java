package org.example.DAO;

import org.example.model.Training;
import org.example.storage.StorageSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TrainingDaoImpl implements TrainingDao {

    private final Map<Long, Training> trainings;
    private static final Logger logger = LoggerFactory.getLogger(TrainingDaoImpl.class);

    @Autowired
    public TrainingDaoImpl(StorageSystem storage) {
        this.trainings = storage.getTrainings();
    }

    @Override
    public void create(Training training) {
        trainings.put(training.getTrainingId(), training);
        logger.info("Training with ID {} created.", training.getTrainingId());
    }

    @Override
    public Training select(Long id) {
        Training training = trainings.get(id);
        if (training == null) {
            logger.warn("Training ID {} not found.", id);
        } else {
            logger.info("Training ID {} retrieved.", id);
        }
        return training;
    }

    @Override
    public void delete(Long id) {
        if (trainings.containsKey(id)) {
            trainings.remove(id);
            logger.info("Training with ID {} deleted.", id);
        } else {
            logger.warn("Training ID {} not found. Delete skipped.", id);
        }
    }

    public List<Training> findAll() {
        logger.info("Retrieving all trainings. Total count: {}", trainings.size());
        return new ArrayList<>(trainings.values());
    }
}
