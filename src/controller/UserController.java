package controller;

import java.sql.SQLException;
import java.util.List;

import database.dao.UserDAO;
import database.model.User;
import service.UserService;

public class UserController {

    private static UserDAO userDAO;
    private static UserService userService;

    static {
        try {
            userDAO = new UserDAO();
            userService = new UserService();
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static List<User> list() throws SQLException {
        return userDAO.selectAll();
    }

    public User getById(String username) throws SQLException {
        return userDAO.selectByUsername(username);
    }

    public void insert(String username, String password) throws SQLException {
        User user = userService.registerUser(username, password);

        // Terminar logica de registrar usuario no service

        userDAO.insert(user);
    }

    public void update(int id, String username, String password) throws SQLException {
        User user = new User(id, username, password);
        userDAO.update(user);
    }

    public void delete(int id) throws SQLException {
        userDAO.delete(id);
    }

}
