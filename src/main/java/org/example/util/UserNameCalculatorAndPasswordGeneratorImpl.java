package org.example.util;

import org.example.DAO.GenericDao;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;


public class UserNameCalculatorAndPasswordGeneratorImpl implements UserNameCalculatorAndPasswordGenerator {

    private <T extends User> boolean userNameInDao(String username, GenericDao<T> users){
        return users.existsMatching(t -> t.getUsername().equals(username));
    }

    private <T extends User> int calculateUserNumber(String username, GenericDao<T> users) {
        int counter = 0;
        String uniqueName = username;

        while (userNameInDao(uniqueName, users)) {
            counter++;
            uniqueName = username + counter;
        }

        return counter;

    }

    public <T extends User> String getUserName(String firstName, String lastName, GenericDao<T> users){

        String userName = firstName + "." + lastName;
        int id = calculateUserNumber(userName, users);
        if (id != 0){
            userName += id;
        }

        return userName;

    }

    public String generateRandomPassword() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 10;
    private static final SecureRandom random = new SecureRandom();

}
