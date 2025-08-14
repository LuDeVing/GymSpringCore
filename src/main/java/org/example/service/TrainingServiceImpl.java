package org.example.service;

import org.example.DAO.GenericDao;
import org.example.model.Trainee;
import org.example.model.Training;
import org.example.model.TrainingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    public void setTrainingDao(GenericDao<Training> trainingDao) {
        this.trainingDao = trainingDao;
    }

    @Override
    public void create(Training training) {
        trainingDao.create(training);
    }

    @Override
    public Optional<Training> select(Long trainingId) {
        return trainingDao.select(trainingId);
    }

    private GenericDao<Training> trainingDao;

}
