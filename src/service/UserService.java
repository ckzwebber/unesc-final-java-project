package service;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import at.favre.lib.crypto.bcrypt.BCrypt;
import database.dao.UserDAO;
import database.model.User;

public class UserService {

	private static UserDAO userDAO;

	static {
		try {
			userDAO = new UserDAO();
		} catch (SQLException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public List<User> list() {
		try {
			return userDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public User getByUsername(String username) {
		if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException("Username cannot be null or empty");
		}

		try {
			User user = userDAO.selectByUsername(username);
			if (user == null) {
				throw new SQLException("User not found");
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void delete(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be greater than zero");
		}

		try {
			User user = userDAO.selectById(id);
			if (user == null) {
				throw new SQLException("User not found");
			}
			userDAO.delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public User login(String username, String password) throws SQLException {
		try {
			if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
				throw new IllegalArgumentException("Username and password can't be null!");
			}

			User user = userOnDatabase(username);
			if (user == null) {
				throw new SQLException("User not found");
			}

			Boolean userVerified = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified;

			if (userVerified) {
				return user;
			} else {
				throw new SQLException("Invalid password");
			}
		} catch (RuntimeException e) {
			return null;
		}
	}

	public User create(String username, String password) {
		try {
			if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
				throw new IllegalArgumentException("Username or password cannot be empty");
			}

			if (username.length() < 3 || password.length() < 8) {
				throw new IllegalArgumentException("Invalid username (min 3) or password (min 8)");
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

			userDAO.insert(user);
			User createdUser = userDAO.selectByUsername(username);
			return createdUser;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private User userOnDatabase(String username) {
		if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException("Username cannot be null or empty");
		}

		try {
			User user = userDAO.selectByUsername(username);

			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
