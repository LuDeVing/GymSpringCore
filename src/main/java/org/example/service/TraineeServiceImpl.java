package org.example.service;

import org.example.DAO.GenericDao;
import org.example.model.Trainee;
import org.example.model.Training;
import org.example.model.User;
import org.example.util.UserNameCalculatorAndPasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {

    @Autowired
    public void setUserPasswordGenerator(UserNameCalculatorAndPasswordGenerator userPasswordGenerator){
        this.userPasswordGenerator = userPasswordGenerator;
    }

    @Autowired
    public void setTraineeDao(GenericDao<Trainee> traineeDao) {
        this.traineeDao = traineeDao;
    }

    @Override
    public void create(User user, LocalDate date, String address) {

        String userName = userPasswordGenerator.getUserName(user.getFirstName(), user.getLastName(), traineeDao);
        String password = userPasswordGenerator.generateRandomPassword();

        Trainee trainee = new Trainee(++currentId, user.getFirstName(), user.getLastName(), userName, password,
                user.isActive(), date, address);

        traineeDao.create(trainee);

    }

    @Override
    public Optional<Trainee> select(Long Id) {
        return traineeDao.select(Id);
    }

    @Override
    public void update(Trainee trainee) {
        traineeDao.update(trainee);
    }

    @Override
    public void delete(Long Id) {
        traineeDao.delete(Id);
        logger.info("Deleted Trainee with ID: {}", Id);
    }

    private GenericDao<Trainee> traineeDao;
    private UserNameCalculatorAndPasswordGenerator userPasswordGenerator;
    private long currentId = 0;

    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);

}
