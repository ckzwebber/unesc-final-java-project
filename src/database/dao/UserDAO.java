package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.ConnectionFactory;
import database.model.User;

public class UserDAO {

	private static final String SELECT_ALL_QUERY = "SELECT id, username FROM tb_users";
	private static final String SELECT_BY_USERNAME_QUERY = "SELECT id, username, password FROM tb_users WHERE username = ?";
	private static final String INSERT_QUERY = "INSERT INTO tb_users(username, password) VALUES (?, ?)";
	private static final String UPDATE_QUERY = "UPDATE tb_users SET password = ? WHERE id = ?";
	private static final String DELETE_QUERY = "DELETE FROM tb_users WHERE id = ?";

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

	public void delete(User user) throws SQLException {
		deleteStatement.setInt(1, user.getId());
		deleteStatement.executeUpdate();
	}

	public ArrayList<User> selectAll() throws SQLException {
		ArrayList<User> userList = new ArrayList<>();
		try (ResultSet resultSet = selectAllStatement.executeQuery()) {
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword("");
				userList.add(user);
			}
		}
		return userList;
	}

	public User selectByUsername(String username) throws SQLException {
		selectByUsernameStatement.setString(1, username);
		try (ResultSet resultSet = selectByUsernameStatement.executeQuery()) {
			if (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				return user;
			}
		}
		return null;
	}

	public User select(User user) throws SQLException {
		return selectByUsername(user.getUsername());
	}
}
