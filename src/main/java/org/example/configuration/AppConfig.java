package org.example.configuration;

import org.example.DAO.*;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.service.*;
import org.example.util.UserPasswordGenerator;
import org.example.util.UserPasswordGeneratorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;


@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = "org.example")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public Map<Long, Trainee> getTraineeMap(){
        return new HashMap<>();
    }

    @Bean
    public Map<Long, Trainer> getTrainerMap(){
        return new HashMap<>();
    }

    @Bean
    public Map<Long, Training> getTrainingMap(){
        return new HashMap<>();
    }

    @Bean
    public UserPasswordGenerator getUserPasswordGenerator(){
        return new UserPasswordGeneratorImpl();
    }

    @Bean
    public TraineeService traineeService(TraineeDao traineeDao, UserPasswordGenerator userPasswordGenerator) {
        TraineeServiceImpl service = new TraineeServiceImpl();
        service.setTraineeDao(traineeDao);
        service.setUserPasswordGenerator(userPasswordGenerator);
        return service;
    }

    @Bean
    public TrainerService trainerService(TrainerDao trainerDao, UserPasswordGenerator userPasswordGenerator) {
        TrainerServiceImpl service = new TrainerServiceImpl();
        service.setTrainerDao(trainerDao);
        service.setUserPasswordGenerator(userPasswordGenerator);
        return service;
    }

    @Bean
    public TrainingService trainingService(TrainingDao trainingDao) {
        TrainingServiceImpl service = new TrainingServiceImpl();
        service.setTrainingDao(trainingDao);
        return service;
    }
        
}