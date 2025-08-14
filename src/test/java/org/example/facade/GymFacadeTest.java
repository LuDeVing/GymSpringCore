package org.example.facade;

import junit.framework.TestCase;
import org.example.DAO.*;
import org.example.model.*;
import org.example.service.*;
import org.example.storage.MapStorage;
import org.example.storage.StorageSystem;
import org.example.util.UserNameCalculatorAndPasswordGeneratorImpl;

import java.time.LocalDate;
import java.util.Optional;

public class GymFacadeTest extends TestCase {

    private GymFacade gymFacade;

    @Override
    protected void setUp() {

        StorageSystem<Trainee> traineeStorage = new MapStorage<>();
        StorageSystem<Trainer> trainerStorage = new MapStorage<>();
        StorageSystem<Training> trainingStorage = new MapStorage<>();

        GenericDao<Trainee> traineeDao = new TraineeDaoImpl(traineeStorage);
        GenericDao<Trainer> trainerDao = new TrainerDaoImpl(trainerStorage);
        GenericDao<Training> trainingDao = new TrainingDaoImpl(trainingStorage);

        TraineeServiceImpl traineeService = new TraineeServiceImpl();
        traineeService.setTraineeDao(traineeDao);
        traineeService.setUserPasswordGenerator(new UserNameCalculatorAndPasswordGeneratorImpl());

        TrainerServiceImpl trainerService = new TrainerServiceImpl();
        trainerService.setTrainerDao(trainerDao);
        trainerService.setUserPasswordGenerator(new UserNameCalculatorAndPasswordGeneratorImpl());

        TrainingServiceImpl trainingService = new TrainingServiceImpl();
        trainingService.setTrainingDao(trainingDao);

        gymFacade = new GymFacadeImpl(traineeService, trainerService, trainingService);

        for (int i = 1; i <= 5; i++) {
            gymFacade.createTrainee(
                    new User("Name" + i, "GVARI" + i, "", "", true),
                    LocalDate.of(1990, 1, i),
                    "Address " + i
            );
        }

        gymFacade.createTrainer(new User("Trainer", "One", "", "", true), "MMA");
        gymFacade.createTrainer(new User("Trainer", "Two", "", "", true), "Boxing");

        gymFacade.createTraining(new Training(
                1L,
                1L,
                "MMA",
                new TrainingType("GROUP"),
                LocalDate.of(2024, 1, 1),
                60
        ));

        gymFacade.createTraining(new Training(
                2L,
                2L,
                "Evening Boxing",
                new TrainingType("INDIVIDUAL"),
                LocalDate.of(2024, 1, 2),
                45
        ));
    }

    public void testSelectTrainee() {
        Optional<Trainee> t = gymFacade.selectTrainee(1L);
        assertTrue(t.isPresent());
        assertEquals("Name1", t.get().getFirstName());
        assertEquals("GVARI1", t.get().getLastName());
    }

    public void testUpdateTrainee() {
        Trainee t = gymFacade.selectTrainee(2L).get();
        t.setAddress("New Address");
        gymFacade.updateTrainee(t);

        Trainee updated = gymFacade.selectTrainee(2L).get();
        assertEquals("New Address", updated.getAddress());
    }

    public void testDeleteTrainee() {
        gymFacade.deleteTrainee(3L);
        assertFalse(gymFacade.selectTrainee(3L).isPresent());
    }

    public void testSelectTrainer() {
        Optional<Trainer> tr = gymFacade.selectTrainer(1L);
        assertTrue(tr.isPresent());
        assertEquals("Trainer", tr.get().getFirstName());
        assertEquals("MMA", tr.get().getSpecialization());
    }

    public void testUpdateTrainer() {
        Trainer tr = gymFacade.selectTrainer(2L).get();
        tr.setSpecialization("Kickboxing");
        gymFacade.updateTrainer(tr);

        Trainer updated = gymFacade.selectTrainer(2L).get();
        assertEquals("Kickboxing", updated.getSpecialization());
    }


}
