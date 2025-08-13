package org.example.service;

import junit.framework.TestCase;
import org.example.DAO.TraineeDao;
import org.example.DAO.TraineeDaoImpl;
import org.example.DAO.TrainingDao;
import org.example.DAO.TrainingDaoImpl;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.TrainingType;
import org.example.storage.StorageSystem;
import org.example.util.UserPasswordGenerator;
import org.example.util.UserPasswordGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceImplTest extends TestCase {

    private TraineeDao traineeDao;
    private TrainingDao trainingDao;
    private UserPasswordGenerator userPasswordGenerator;

    private TraineeServiceImpl traineeService;
    private TrainingServiceImpl trainingService;

    private Map<Long, Trainee> traineeMap;
    private Map<Long, Trainer> trainerMap;
    private Map<Long, Training> trainingMap;


    @BeforeEach
    public void setUp() {

        traineeMap = new HashMap<>();
        trainerMap = new HashMap<>();
        trainingMap = new HashMap<>();

        StorageSystem storageSystem = new StorageSystem(trainerMap, traineeMap, trainingMap);

        traineeDao = new TraineeDaoImpl(storageSystem);
        trainingDao = new TrainingDaoImpl(storageSystem);
        userPasswordGenerator = new UserPasswordGeneratorImpl();

        traineeService = new TraineeServiceImpl();
        traineeService.setTraineeDao(traineeDao);
        traineeService.setTrainingDao(trainingDao);
        traineeService.setUserPasswordGenerator(userPasswordGenerator);

        trainingService = new TrainingServiceImpl();
        trainingService.setTrainingDao(trainingDao);

    }

    @Test
    public void testCreateGeneratesUniqueUsernameAndPassword() {

        traineeService.create("Alice", "Wonderlandson", true, LocalDate.of(1992, 3, 3), "addr1");

        Trainee trainee = traineeService.select(1L);

        assertTrue(trainee.getUsername().toLowerCase().contains("alice"));
        assertTrue(trainee.getUsername().toLowerCase().contains("wonderlandson"));
        assertTrue(trainee.isActive());
        assertEquals(10, trainee.getPassword().length());

        traineeService.create("Alice", "Wonderlandson", true, LocalDate.of(1995, 3, 3), "addr2");
        assertTrue(traineeService.select(2L).getUsername().endsWith("1"));

        traineeService.create("Alice", "Wonderlandson", true, LocalDate.of(1999, 3, 3), "addr3");
        assertTrue(traineeService.select(3L).getUsername().endsWith("2"));

    }

    @Test
    public void testDeleteAlsoDeletesTrainings() {

        Long traineeId = 1L;

        LocalDate date = LocalDate.now();

        trainingService.create(traineeId, 1L, "Cardio Session", "CARDIO", date, 60);
        trainingService.create(traineeId, 2L, "Strength Session", "STRENGTH", date, 45);

        traineeService.delete(traineeId);

        assertNull(trainingService.select(Training.calculateId(traineeId, 1L, date, new TrainingType("CARDIO"))));
        assertNull(trainingService.select(Training.calculateId(traineeId, 2L, date, new TrainingType("STRENGTH"))));

    }


}