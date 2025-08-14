package org.example.facade;

import org.example.model.*;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class GymFacadeImpl implements GymFacade {

    @Autowired
    public GymFacadeImpl(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    @Override
    public void createTrainee(User user, LocalDate dateOfBirth, String address) {
        traineeService.create(user, dateOfBirth, address);
        logger.info("Created Trainee: {} {}", user.getFirstName(), user.getLastName());
    }

    @Override
    public Optional<Trainee> selectTrainee(Long id) {
        Optional<Trainee> trainee = traineeService.select(id);
        if (trainee.isEmpty()) {
            logger.warn("Trainee with ID {} not found.", id);
        } else {
            logger.info("Selected Trainee with ID: {}", id);
        }
        return trainee;
    }

    @Override
    public void updateTrainee(Trainee trainee) {
        traineeService.update(trainee);
        logger.info("Updated Trainee with ID: {}", trainee.getUserId());
    }

    @Override
    public void deleteTrainee(Long id) {
        traineeService.delete(id);
        logger.info("Deleted Trainee with ID: {}", id);
    }

    @Override
    public void createTrainer(User user, String specialization) {
        trainerService.create(user, specialization);
        logger.info("Created Trainer: {} {}", user.getFirstName(), user.getLastName());
    }

    @Override
    public Optional<Trainer> selectTrainer(Long id) {
        Optional<Trainer> trainer = trainerService.select(id);
        if (trainer.isEmpty()) {
            logger.warn("Trainer with ID {} not found.", id);
        } else {
            logger.info("Selected Trainer with ID: {}", id);
        }
        return trainer;
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        trainerService.update(trainer);
        logger.info("Updated Trainer with ID: {}", trainer.getUserId());
    }

    @Override
    public void createTraining(Training training) {
        trainingService.create(training);
        logger.info("Created Training '{}' for Trainee ID {} and Trainer ID {}",
                training.getTrainingName(), training.getTraineeId(), training.getTrainerId());
    }

    @Override
    public Optional<Training> selectTraining(Long traineeId, Long trainerId, LocalDate date, TrainingType trainingType) {

        Long id = Training.calculateId(traineeId, trainerId, date, trainingType);

        Optional<Training> training = trainingService.select(id);
        if (training.isEmpty()) {
            logger.warn("Training with ID {} not found.", id);
        } else {
            logger.info("Selected Training with ID: {}", id);
        }
        return training;
    }

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    private static final Logger logger = LoggerFactory.getLogger(GymFacadeImpl.class);

}
