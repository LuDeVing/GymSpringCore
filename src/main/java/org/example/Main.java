package org.example;

import org.example.configuration.AppConfig;
import org.example.facade.GymFacade;
import org.example.util.DataLoad;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        GymFacade gymFacade = context.getBean(GymFacade.class);

        LocalDate date = LocalDate.of(2000, 2, 3);

        gymFacade.createTrainee("John", "Doe", true, date, "address");

        System.out.println("All trainees:");
        System.out.println(gymFacade.selectTrainee(1L).getUsername());
        System.out.println(gymFacade.selectTrainee(2L).getUsername());

    }
}
