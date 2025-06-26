package service;

import java.sql.SQLException;

import at.favre.lib.crypto.bcrypt.BCrypt;
import database.dao.UserDAO;
import database.model.User;

public class UserService {

    public boolean loginUser(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        try {
            User user = userOnDatabase(username);
            return BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public User registerUser(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be empty");
        }

        if (username.length() < 3 || password.length() < 8) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        User userOnDatabase = userOnDatabase(username);
        if (userOnDatabase != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        int saltLength = 12;
        String hashedPassword = BCrypt.withDefaults().hashToString(saltLength, password.toCharArray());

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        return user;
    }

    public void updateUserProfile(int userId, String newUsername, String newPassword) {

    }

    public User userOnDatabase(String username) {
        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.selectByUsername(username);
            if (user == null) {
                throw new SQLException("User not found");
            }

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error accessing user data", e);
        }
    }
}
