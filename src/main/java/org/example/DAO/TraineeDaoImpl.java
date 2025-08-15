package org.example.DAO;

import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.storage.StorageSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Predicate;

@Repository
public class TraineeDaoImpl implements GenericDao<Trainee> {

    private final StorageSystem<Trainee> trainees;

    @Autowired
    public TraineeDaoImpl(StorageSystem<Trainee> storageSystem) {
        this.trainees = storageSystem;
    }


    @Override
    public void create(Trainee trainee) {
        trainees.put(trainee.getUserId(), trainee);
    }

    @Override
    public Optional<Trainee> select(Long Id) {
        return trainees.findById(Id);
    }

    @Override
    public void update(Trainee trainee) {
        trainees.update(trainee.getUserId(), trainee);
    }

    @Override
    public void delete(Long Id) {
        trainees.delete(Id);
    }

    @Override
    public boolean existsMatching(Predicate<Trainee> predicate) {
        return trainees.existsMatching(predicate);
    }

}
