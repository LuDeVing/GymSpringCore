package org.example.DAO;

import org.example.model.Trainer;
import org.example.storage.StorageSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TrainerDaoImpl implements TrainerDao {

    private final Map<Long, Trainer> trainers;
    private static final Logger logger = LoggerFactory.getLogger(TrainerDaoImpl.class);

    @Autowired
    public TrainerDaoImpl(StorageSystem storage){
        trainers = storage.getTrainers();
    }

    @Override
    public void create(Trainer trainer) {
        if (!trainers.containsKey(trainer.getUserId())) {
            trainers.put(trainer.getUserId(), trainer);
            logger.info("Trainer with ID {} created.", trainer.getUserId());
        } else {
            logger.warn("Trainer with ID {} already exists. Creation skipped.", trainer.getUserId());
        }
    }

    @Override
    public Trainer select(Long Id) {
        Trainer trainer = trainers.get(Id);
        if (trainer == null) {
            logger.warn("Trainer ID {} not found.", Id);
        } else {
            logger.info("Trainer ID {} retrieved.", Id);
        }
        return trainer;
    }

    @Override
    public void update(Trainer trainer) {
        if (trainers.containsKey(trainer.getUserId())) {
            trainers.put(trainer.getUserId(), trainer);
            logger.info("Trainer with ID {} updated.", trainer.getUserId());
        } else {
            logger.warn("Trainer ID {} does not exist. Update skipped.", trainer.getUserId());
        }
    }

    @Override
    public List<Trainer> findAll() {
        logger.info("Retrieving all trainers. Total count: {}", trainers.size());
        return new ArrayList<>(trainers.values());
    }

}
