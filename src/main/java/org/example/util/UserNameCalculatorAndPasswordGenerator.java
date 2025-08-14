package org.example.util;

import org.example.DAO.GenericDao;
import org.example.model.User;

import java.util.List;

public interface UserNameCalculatorAndPasswordGenerator {

    <T extends User> String getUserName(String firstName, String lastName, GenericDao<T> users);
    String generateRandomPassword();
}
