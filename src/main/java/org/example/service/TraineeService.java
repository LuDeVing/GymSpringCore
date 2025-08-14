package org.example.service;

import org.example.model.Trainee;
import org.example.model.User;

import java.time.LocalDate;
import java.util.Optional;

public interface TraineeService {
    void create(User user, LocalDate date, String address);
    Optional<Trainee> select(Long Id);
    void update(Trainee trainee);
    void delete(Long Id);
}
