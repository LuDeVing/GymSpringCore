package org.example.facade;

import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.TrainingType;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GymFacadeImpl implements GymFacade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    private static final Logger logger = LoggerFactory.getLogger(GymFacadeImpl.class);

    @Autowired
    public GymFacadeImpl(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    @Override
    public void createTrainee(String firstName, String lastName, boolean isActive, LocalDate dateOfBirth, String address) {
        traineeService.create(firstName, lastName, isActive, dateOfBirth, address);
        logger.info("Created Trainee: {} {}", firstName, lastName);
    }

    @Override
    public Trainee selectTrainee(Long id) {
        Trainee trainee = traineeService.select(id);
        if (trainee == null) {
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
    public void createTrainer(String firstName, String lastName, boolean isActive, String specialization) {
        trainerService.create(firstName, lastName, isActive, specialization);
        logger.info("Created Trainer: {} {}", firstName, lastName);
    }

    @Override
    public Trainer selectTrainer(Long id) {
        Trainer trainer = trainerService.select(id);
        if (trainer == null) {
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
    public void createTraining(Long traineeId, Long trainerId, String trainingName, String trainingTypeName, LocalDate trainingDate, int trainingDuration) {
        trainingService.create(traineeId, trainerId, trainingName, trainingTypeName, trainingDate, trainingDuration);
        logger.info("Created Training '{}' for Trainee ID {} and Trainer ID {}", trainingName, traineeId, trainerId);
    }

    @Override
    public Training selectTraining(Long traineeId, Long trainerId, LocalDate date, TrainingType trainingType) {

        Long id = Training.calculateId(traineeId, trainerId, date, trainingType);

        Training training = trainingService.select(id);
        if (training == null) {
            logger.warn("Training with ID {} not found.", id);
        } else {
            logger.info("Selected Training with ID: {}", id);
        }
        return training;
    }
}
