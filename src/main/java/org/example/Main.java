package org.example;

import org.example.configuration.AppConfig;
import org.example.facade.GymFacade;
import org.example.model.Trainee;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        GymFacade gymFacade = context.getBean(GymFacade.class);

        LocalDate date = LocalDate.of(2000, 2, 3);

        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setActive(true);

        gymFacade.createTrainee(trainee, date, "address");

        System.out.println("All trainees:");
        System.out.println(gymFacade.selectTrainee(1L).get().getUsername());

        Trainee trainee2 = new Trainee();
        trainee2.setFirstName("Jane");
        trainee2.setLastName("Smith");
        trainee2.setActive(true);
        gymFacade.createTrainee(trainee2, date, "address 2");

        System.out.println(gymFacade.selectTrainee(2L).get().getUsername());

    }

}
