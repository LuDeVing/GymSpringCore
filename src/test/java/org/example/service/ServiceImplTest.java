package org.example.service;

import junit.framework.TestCase;
import org.example.DAO.GenericDao;
import org.example.DAO.TraineeDaoImpl;
import org.example.model.*;
import org.example.storage.MapStorage;
import org.example.storage.StorageSystem;
import org.example.util.PasswordGeneratorImpl;
import org.example.util.UserNameCalculatorImpl;

import java.time.LocalDate;


public class ServiceImplTest extends TestCase {


    TraineeServiceImpl traineeService;

    @Override
    protected void setUp() {

        StorageSystem<Trainee> traineeStorageSystem = new MapStorage<>();

        GenericDao<Trainee> trainees = new TraineeDaoImpl(traineeStorageSystem);

        traineeService = new TraineeServiceImpl(trainees, new UserNameCalculatorImpl(), new PasswordGeneratorImpl());

        User user1 = new User("Name", "Gvari", "", "", true);
        User user2 = new User("Name", "Gvari", "", "", true);
        User user3 = new User("Name", "Gvari", "", "", true);
        User user4 = new User("Name", "Gvari", "", "", true);
        User user5 = new User("Name", "Gvari", "", "", true);

        traineeService.create(user1, LocalDate.of(1990, 1, 1), "Address 1");
        traineeService.create(user2, LocalDate.of(1990, 1, 1), "Address 2");
        traineeService.create(user3, LocalDate.of(1990, 1, 1), "Address 3");
        traineeService.create(user4, LocalDate.of(1990, 1, 1), "Address 4");
        traineeService.create(user5, LocalDate.of(1990, 1, 1), "Address 5");

    }

    public void testIdGeneration() {

        User test1 = new User("Name", "Gvari", "", "", true);
        User test2 = new User("Name", "Gvari", "", "", true);

        traineeService.create(test1, LocalDate.of(1990, 1, 1), "Address 4");
        traineeService.create(test2, LocalDate.of(1990, 1, 1), "Address 5");

        assertTrue(traineeService.select(6L).isPresent());
        assertTrue(traineeService.select(7L).isPresent());

    }

    public void testGeneratesUserNameAndPassword() {

        assert(traineeService.select(1L).isPresent());
        assert(traineeService.select(2L).isPresent());
        assert(traineeService.select(3L).isPresent());
        assert(traineeService.select(4L).isPresent());
        assert(traineeService.select(5L).isPresent());

        assertEquals("Name.Gvari", traineeService.select(1L).get().getUsername());
        assertEquals("Name.Gvari1", traineeService.select(2L).get().getUsername());
        assertEquals("Name.Gvari2", traineeService.select(3L).get().getUsername());
        assertEquals("Name.Gvari3", traineeService.select(4L).get().getUsername());
        assertEquals("Name.Gvari4", traineeService.select(5L).get().getUsername());

    }


}