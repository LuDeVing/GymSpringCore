package org.example.util;

import org.example.DAO.GenericDao;
import org.example.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserNameCalculatorImpl implements UserNameCalculator{

    public <T extends User> String getUserName(String firstName, String lastName, GenericDao<T> users) {

        String userName = firstName + "." + lastName;
        int id = calculateUserNumber(userName, users);
        if (id != 0) {
            userName += id;
        }

        return userName;

    }

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

}
