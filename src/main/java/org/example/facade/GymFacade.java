package org.example.facade;

import org.example.model.*;

import java.time.LocalDate;
import java.util.Optional;

public interface GymFacade {

    void createTrainee(User user, LocalDate dateOfBirth, String address);
    Optional<Trainee> selectTrainee(Long id);
    void updateTrainee(Trainee trainee);
    void deleteTrainee(Long id);

    void createTrainer(User user, String specialization);
    Optional<Trainer> selectTrainer(Long id);
    void updateTrainer(Trainer trainer);

    void createTraining(Training training);
    Optional<Training> selectTraining(Long traineeId, Long trainerId, LocalDate date, TrainingType trainingType);

}
