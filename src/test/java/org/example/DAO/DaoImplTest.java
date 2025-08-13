package org.example.DAO;

import junit.framework.TestCase;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.TrainingType;
import org.example.storage.StorageSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DaoImplTest extends TestCase {

    private TraineeDaoImpl traineeDao;
    private TrainerDaoImpl trainerDao;
    private TrainingDaoImpl trainingDao;

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
        trainerDao = new TrainerDaoImpl(storageSystem);
        trainingDao = new TrainingDaoImpl(storageSystem);
    }


    @Test
    public void testTraineeDao() {
        Trainee trainee = new Trainee(1L, "John", "Johnson", "jonhUser",
                "password", true, LocalDate.of(1990, 1, 1), "123 John St");

        traineeDao.create(trainee);
        assertEquals("John", traineeDao.select(1L).getFirstName());
        trainee.setFirstName("Johnny");
        traineeDao.update(trainee);

        assertEquals("Johnny", traineeDao.select(1L).getFirstName());
        assertTrue(traineeDao.findAll().contains(trainee));

        traineeDao.delete(1L);
        assertNull(traineeDao.select(1L));
    }

    @Test
    public void testTrainerDao() {
        Trainer trainer = new Trainer(2L, "Alice", "Wonderlandson", "aliceUser",
                "pass123", true, "Yoga");

        trainerDao.create(trainer);
        assertEquals("Alice", trainerDao.select(2L).getFirstName());

        trainer.setSpecialization("Pilates");
        trainerDao.update(trainer);
        assertEquals("Pilates", trainerDao.select(2L).getSpecialization());

    }

    @Test
    public void testTrainingDao() {

        Training training = new Training(1L, 2L, "Morning Workout",
                new TrainingType("Morning type training"), LocalDate.now(), 60);

        trainingDao.create(training);
        assertEquals("Morning Workout", trainingDao.select(training.getTrainingId()).getTrainingName());

    }

}