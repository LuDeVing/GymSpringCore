package org.example.service;

import org.example.DAO.GenericDao;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.User;
import org.example.util.UserNameCalculatorAndPasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    public void setTrainerDao(GenericDao<Trainer> trainerDao) {
        this.trainerDao = trainerDao;
    }

    @Autowired
    public void setUserPasswordGenerator(UserNameCalculatorAndPasswordGenerator userPasswordGenerator) {
        this.userPasswordGenerator = userPasswordGenerator;
    }

    @Override
    public void create(User user, String specialization) {

        String userName = userPasswordGenerator.getUserName(user.getFirstName(), user.getLastName(), trainerDao);
        String password = userPasswordGenerator.generateRandomPassword();

        Trainer trainer = new Trainer(++currentId, user.getFirstName(), user.getLastName(), userName, password,
                user.isActive(), specialization);

        trainerDao.create(trainer);

    }

    @Override
    public Optional<Trainer> select(Long id) {
        return trainerDao.select(id);
    }

    @Override
    public void update(Trainer trainer) {
        trainerDao.update(trainer);
    }

    private GenericDao<Trainer> trainerDao;
    private UserNameCalculatorAndPasswordGenerator userPasswordGenerator;
    private long currentId = 0;

}
