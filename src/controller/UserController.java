package controller;

import java.sql.SQLException;
import java.util.List;

import database.model.User;
import service.UserService;

public class UserController {

	private static UserService userService;

	static {
		userService = new UserService();
	}

	public static List<User> list() throws SQLException {
		List<User> users = userService.list();
		return users;
	}

	public static User getByUsername(String username) throws SQLException {
		User user = userService.getByUsername(username);
		return user;
	}

	public static User insert(String username, String password) throws SQLException {
		User user = userService.create(username, password);
		return user;
	}

	public static User login(String username, String password) throws SQLException {
		User user = userService.login(username, password);
		return user;
	}

	public static void delete(int id) throws SQLException {
		userService.delete(id);
	}
}