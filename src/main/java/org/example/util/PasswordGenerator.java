package org.example.util;

import org.example.DAO.GenericDao;
import org.example.model.User;

import java.util.List;

public interface PasswordGenerator {
    String generateRandomPassword();
}
