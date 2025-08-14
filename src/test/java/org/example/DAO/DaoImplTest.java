package org.example.DAO;

import junit.framework.TestCase;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.TrainingType;
import org.example.storage.MapStorage;
import org.example.storage.StorageSystem;

import java.time.LocalDate;


public class DaoImplTest extends TestCase {

    private GenericDao<Trainee> trainees;
    private GenericDao<Trainer> trainers;
    private GenericDao<Training> trainings;

    private Training training1;

    private Trainer tr1;

    private Trainee t1;

    @Override
    protected void setUp() {

        StorageSystem<Trainee> traineeStorageSystem = new MapStorage<>();
        StorageSystem<Trainer> trainerStorageSystem = new MapStorage<>();
        StorageSystem<Training> trainingStorageSystem = new MapStorage<>();

        trainees = new TraineeDaoImpl(traineeStorageSystem);
        trainers = new TrainerDaoImpl(trainerStorageSystem);
        trainings = new TrainingDaoImpl(trainingStorageSystem);

        t1 = new Trainee(
                1L, "John", "Doe", "jdoe", "pass123",
                true, LocalDate.of(1990, 1, 1), "Address 1"
        );
        Trainee t2 = new Trainee(
                2L, "Jane", "jameson", "jjameson", "pass456",
                true, LocalDate.of(1992, 5, 10), "Address 2"
        );
        trainees.create(t1);
        trainees.create(t2);

        tr1 = new Trainer(
                1L, "Mike", "Trainer", "mtrainer", "pass111",
                true, "Yoga"
        );
        Trainer tr2 = new Trainer(
                2L, "Sara", "Coach", "scoach", "pass222",
                true, "Pilates"
        );
        trainers.create(tr1);
        trainers.create(tr2);

        training1 = new Training(
                1L, 1L, "Morning Yoga",
                new TrainingType("Yoga"),
                LocalDate.of(2023, 1, 1), 60
        );
        Training training2 = new Training(
                2L, 2L, "Evening Pilates",
                new TrainingType("Pilates"),
                LocalDate.of(2023, 2, 1), 45
        );
        trainings.create(training1);
        trainings.create(training2);
    }


    public void testTraineeDaoUserName() {
        assertTrue(trainees.select(2L).isPresent());
        assertEquals("jjameson", trainees.select(2L).get().getUsername());
    }

    public void testTrainerDaoUserName() {
        assertTrue(trainers.select(2L).isPresent());
        assertEquals("scoach", trainers.select(2L).get().getUsername());
    }

    public void testTrainingDao() {
        assertTrue(trainings.select(training1.getTrainingId()).isPresent());
        assertEquals(60, trainings.select(training1.getTrainingId()).get().getTrainingDuration());
    }

    public void testUpdateTrainee() {
        t1.setUsername("johnny");
        trainees.update(t1);

        assert(trainees.select(1L).isPresent());

        assertEquals("johnny", trainees.select(1L).get().getUsername());
    }

    public void testUpdateTrainer() {
        tr1.setUsername("mikeT");
        trainers.update(tr1);

        assert(trainers.select(1L).isPresent());


        assertEquals("mikeT", trainers.select(1L).get().getUsername());
    }

    public void testUpdateTraining() {
        training1.setTrainingDuration(75);
        trainings.update(training1);

        assert(trainings.select(training1.getTrainingId()).isPresent());

        assertEquals(75, trainings.select(training1.getTrainingId()).get().getTrainingDuration());
    }

    public void testDeleteTrainee() {
        trainees.delete(1L);
        assertFalse(trainees.select(1L).isPresent());
    }

    public void testDeleteTrainer() {
        trainers.delete(1L);
        assertFalse(trainers.select(1L).isPresent());
    }

    public void testDeleteTraining() {
        trainings.delete(training1.getTrainingId());
        assertFalse(trainings.select(training1.getTrainingId()).isPresent());
    }

}