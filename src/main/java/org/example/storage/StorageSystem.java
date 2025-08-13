package org.example.storage;

import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class StorageSystem {

    private final Map<Long, Trainer> trainers;
    private final Map<Long, Trainee> trainees;
    private final Map<Long, Training> trainings;

    @Autowired
    public StorageSystem(Map<Long, Trainer> trainers,
                         Map<Long, Trainee> trainees,
                         Map<Long, Training> trainings) {
        this.trainers = trainers;
        this.trainees = trainees;
        this.trainings = trainings;
    }


    public Map<Long, Trainer> getTrainers() {
        return trainers;
    }

    public Map<Long, Trainee> getTrainees() {
        return trainees;
    }

    public Map<Long, Training> getTrainings() {
        return trainings;
    }

}
