package org.example.service;

import org.example.DAO.GenericDao;
import org.example.model.Trainee;
import org.example.model.User;
import org.example.util.PasswordGenerator;
import org.example.util.UserNameCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {

    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);

    private GenericDao<Trainee> traineeDao;
    private UserNameCalculator userNameCalculator;
    private PasswordGenerator passwordGenerator;
    private long currentId = 0;

    @Autowired
    public TraineeServiceImpl(GenericDao<Trainee> traineeDao,
                              UserNameCalculator userNameCalculator,
                              PasswordGenerator passwordGenerator) {
        this.traineeDao = traineeDao;
        this.userNameCalculator = userNameCalculator;
        this.passwordGenerator = passwordGenerator;
    }

    @Override
    public void create(User user, LocalDate date, String address) {

        String userName = userNameCalculator.getUserName(user.getFirstName(), user.getLastName(), traineeDao);
        String password = passwordGenerator.generateRandomPassword();

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

}
