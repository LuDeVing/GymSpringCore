package org.example.DAO;

import org.example.model.Trainee;
import org.example.storage.StorageSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TraineeDaoImpl implements TraineeDao {

    private final Map<Long, Trainee> trainees;
    private static final Logger logger = LoggerFactory.getLogger(TraineeDaoImpl.class);


    @Autowired
    public TraineeDaoImpl(StorageSystem storageSystem) {
        this.trainees = storageSystem.getTrainees();
    }


    @Override
    public void create(Trainee trainee) {
        if(!trainees.containsKey(trainee.getUserId())) {
            trainees.put(trainee.getUserId(), trainee);
            logger.info("Trainee with ID {} created.", trainee.getUserId());
        }
        else{
            logger.info("Trainee {} is already in the database!", trainee.getUserId());
        }
    }

    @Override
    public Trainee select(Long Id) {

        if(trainees.containsKey(Id)) {
            return trainees.get(Id);
        }
        else{
            logger.warn("Trainee ID {} does not exist in the database, selected null!", Id);
        }

        return null;

    }

    @Override
    public void update(Trainee trainee) {
        if(trainees.containsKey(trainee.getUserId())){
            trainees.put(trainee.getUserId(), trainee);
        }
        else{
            logger.warn("Trainee ID {} does not exist in the database, cannot update!", trainee.getUserId());
        }
    }

    @Override
    public void delete(Long Id) {
        if (trainees.containsKey(Id)) {
            trainees.remove(Id);
        } else {
            logger.info("Trainee ID {} does not exist in the database, cannot delete!", Id);
        }
    }

    public List<Trainee> findAll() {
        return new ArrayList<>(trainees.values());
    }

}
