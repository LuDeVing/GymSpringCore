package org.example.facade;

import junit.framework.TestCase;
import org.example.DAO.*;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.TrainingType;
import org.example.service.*;
import org.example.storage.StorageSystem;
import org.example.util.UserPasswordGenerator;
import org.example.util.UserPasswordGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;

public class GymFacadeTest extends TestCase {

    private GymFacade gymFacade;

    @BeforeEach
    public void setUp() {
        StorageSystem storageSystem = new StorageSystem(
                new HashMap<>(), new HashMap<>(), new HashMap<>()
        );

        TraineeDao traineeDao = new TraineeDaoImpl(storageSystem);
        TrainerDao trainerDao = new TrainerDaoImpl(storageSystem);
        TrainingDao trainingDao = new TrainingDaoImpl(storageSystem);

        UserPasswordGenerator userPasswordGenerator = new UserPasswordGeneratorImpl();

        TraineeServiceImpl traineeService = new TraineeServiceImpl();
        traineeService.setTraineeDao(traineeDao);
        traineeService.setTrainingDao(trainingDao);
        traineeService.setUserPasswordGenerator(userPasswordGenerator);

        TrainerServiceImpl trainerService = new TrainerServiceImpl();
        trainerService.setTrainerDao(trainerDao);
        trainerService.setUserPasswordGenerator(userPasswordGenerator);

        TrainingServiceImpl trainingService = new TrainingServiceImpl();
        trainingService.setTrainingDao(trainingDao);

        gymFacade = new GymFacadeImpl(traineeService, trainerService, trainingService);
    }


    @Test
    public void testSelectTrainee() {

        gymFacade.createTrainee(
                "John", "Johnson", true,
                java.time.LocalDate.of(1990, 1, 1), "addr1"
        );

        Trainee trainee = gymFacade.selectTrainee(1L);

        assertNotNull(trainee);
        assertEquals("John", trainee.getFirstName());
        assertEquals("Johnson", trainee.getLastName());
    }

    @Test
    public void testCreateTrainerAndSelect() {
        gymFacade.createTrainer("Mike", "Mika", true, "Fitness");

        Trainer trainer = gymFacade.selectTrainer(1L);

        assertNotNull(trainer);
        assertEquals("Mike", trainer.getFirstName());
        assertEquals("Mika", trainer.getLastName());
        assertEquals("Fitness", trainer.getSpecialization());
    }


    @Test
    public void testUpdateTrainee() {
        gymFacade.createTrainee("John", "Johnson", true, java.time.LocalDate.of(1990, 1, 1), "addr1");
        Trainee trainee = gymFacade.selectTrainee(1L);
        trainee.setFirstName("Johnny");
        gymFacade.updateTrainee(trainee);

        Trainee updated = gymFacade.selectTrainee(1L);
        assertEquals("Johnny", updated.getFirstName());
    }


}
