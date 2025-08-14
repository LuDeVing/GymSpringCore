package org.example.service;

import org.example.model.Trainer;
import org.example.model.User;

import java.time.LocalDate;
import java.util.Optional;

public interface TrainerService {
    void create(User user, String specialization);
    Optional<Trainer> select(Long id);
    void update(Trainer trainer);

}
