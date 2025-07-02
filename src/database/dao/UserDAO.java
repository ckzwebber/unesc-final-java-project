package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.ConnectionFactory;
import database.model.User;

public class UserDAO {

	private static final String SELECT_ALL_QUERY = "SELECT id, username FROM users";
	private static final String SELECT_BY_USERNAME_QUERY = "SELECT id, username, password FROM users WHERE username = ?";
	private static final String INSERT_QUERY = "INSERT INTO users(username, password) VALUES (?, ?)";
	private static final String UPDATE_QUERY = "UPDATE users SET password = ? WHERE id = ?";
	private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";

	private final PreparedStatement selectAllStatement;
	private final PreparedStatement selectByUsernameStatement;
	private final PreparedStatement insertStatement;
	private final PreparedStatement updateStatement;
	private final PreparedStatement deleteStatement;

	public UserDAO() throws SQLException {
		Connection connection = ConnectionFactory.getConnection();
		selectAllStatement = connection.prepareStatement(SELECT_ALL_QUERY);
		selectByUsernameStatement = connection.prepareStatement(SELECT_BY_USERNAME_QUERY);
		insertStatement = connection.prepareStatement(INSERT_QUERY);
		updateStatement = connection.prepareStatement(UPDATE_QUERY);
		deleteStatement = connection.prepareStatement(DELETE_QUERY);
	}

	public void insert(User user) throws SQLException {
		insertStatement.setString(1, user.getUsername());
		insertStatement.setString(2, user.getPassword());
		insertStatement.executeUpdate();
	}

	public void update(User user) throws SQLException {
		updateStatement.setString(1, user.getPassword());
		updateStatement.setInt(2, user.getId());
		updateStatement.executeUpdate();
	}

	public void delete(int id) throws SQLException {
		deleteStatement.setInt(1, id);
		deleteStatement.executeUpdate();
	}

	public ArrayList<User> selectAll() throws SQLException {
		ArrayList<User> userList = new ArrayList<>();
		try (ResultSet resultSet = selectAllStatement.executeQuery()) {
			while (resultSet.next()) {
				User user = buildUserFromResultSet(resultSet);
				userList.add(user);
			}
		}
		return userList;
	}

	public User selectByUsername(String username) throws SQLException {
		selectByUsernameStatement.setString(1, username);
		try (ResultSet resultSet = selectByUsernameStatement.executeQuery()) {
			if (resultSet.next()) {
				User user = buildUserFromResultSet(resultSet);
				return user;
			}
		}
		return null;
	}

	private User buildUserFromResultSet(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getInt("id"));
		user.setUsername(resultSet.getString("username"));
		user.setPassword(resultSet.getString("password"));
		return user;
	}
}
