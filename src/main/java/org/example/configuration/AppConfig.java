package org.example.configuration;

import org.example.DAO.*;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.service.*;
import org.example.storage.MapStorage;
import org.example.storage.StorageSystem;
import org.example.util.UserNameCalculatorAndPasswordGenerator;
import org.example.util.UserNameCalculatorAndPasswordGeneratorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = "org.example")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public StorageSystem<Trainee> traineeStorage() {
        return new MapStorage<>();
    }

    @Bean
    public StorageSystem<Trainer> trainerStorage() {
        return new MapStorage<>();
    }

    @Bean
    public StorageSystem<Training> trainingStorage() {
        return new MapStorage<>();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public UserNameCalculatorAndPasswordGenerator getUserPasswordGenerator(){
        return new UserNameCalculatorAndPasswordGeneratorImpl();
    }

    @Bean
    public TraineeService traineeService(GenericDao<Trainee> traineeDao, UserNameCalculatorAndPasswordGenerator userPasswordGenerator) {
        TraineeServiceImpl service = new TraineeServiceImpl();
        service.setTraineeDao(traineeDao);
        service.setUserPasswordGenerator(userPasswordGenerator);
        return service;
    }

    @Bean
    public TrainerService trainerService(GenericDao<Trainer> trainerDao, UserNameCalculatorAndPasswordGenerator userPasswordGenerator) {
        TrainerServiceImpl service = new TrainerServiceImpl();
        service.setTrainerDao(trainerDao);
        service.setUserPasswordGenerator(userPasswordGenerator);
        return service;
    }

    @Bean
    public TrainingService trainingService(GenericDao<Training> trainingDao) {
        TrainingServiceImpl service = new TrainingServiceImpl();
        service.setTrainingDao(trainingDao);
        return service;
    }
        
}