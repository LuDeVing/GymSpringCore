package org.example.configuration;

import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.storage.MapStorage;
import org.example.storage.StorageSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

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
        
}