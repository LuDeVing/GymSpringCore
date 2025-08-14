package org.example.service;

import org.example.model.Training;

import java.time.LocalDate;
import java.util.Optional;

public interface TrainingService {
    void create(Training training);

    Optional<Training> select(Long trainingId);
}