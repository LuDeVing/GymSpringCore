package org.example.service;

import org.example.DAO.GenericDao;
import org.example.model.Trainer;
import org.example.model.User;
import org.example.util.PasswordGenerator;
import org.example.util.UserNameCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {

    private GenericDao<Trainer> trainerDao;
    private UserNameCalculator userNameCalculator;
    private PasswordGenerator passwordGenerator;
    private long currentId = 0;

    @Autowired
    public TrainerServiceImpl(GenericDao<Trainer> trainerDao,
                              UserNameCalculator userNameCalculator,
                              PasswordGenerator passwordGenerator) {
        this.trainerDao = trainerDao;
        this.userNameCalculator = userNameCalculator;
        this.passwordGenerator = passwordGenerator;
    }

    @Override
    public void create(User user, String specialization) {

        String userName = userNameCalculator.getUserName(user.getFirstName(), user.getLastName(), trainerDao);
        String password = passwordGenerator.generateRandomPassword();

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

}
