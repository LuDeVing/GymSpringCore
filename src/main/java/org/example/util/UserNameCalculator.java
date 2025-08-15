package org.example.util;

import org.example.DAO.GenericDao;
import org.example.model.User;

public interface UserNameCalculator {
    <T extends User> String getUserName(String firstName, String lastName, GenericDao<T> users);
}
