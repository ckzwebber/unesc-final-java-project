package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.model.User;

public class UserDAO {

	private String selectAll = "SELECT username FROM tb_users";
	private String selectWhere = "SELECT * FROM users WHERE username = ?";
	private String insert = "INSERT INTO users(username, password) VALUES (?, ?)";
	private String update = "UPDATE users SET password = ? WHERE username = ?";
	private String delete = "DELETE FROM users WHERE id = ?";

	private PreparedStatement pstSelectAll;
	private PreparedStatement pstSelectWhere;
	private PreparedStatement pstInsert;
	private PreparedStatement pstUpdate;
	private PreparedStatement pstDelete;

	public UserDAO(Connection conn) throws SQLException {
		pstSelectAll = conn.prepareStatement(selectAll);
		pstSelectWhere = conn.prepareStatement(selectWhere);
		pstInsert = conn.prepareStatement(insert);
		pstUpdate = conn.prepareStatement(update);
		pstDelete = conn.prepareStatement(delete);
	}

	public void insert(String username, String password) throws SQLException {
		pstInsert.setString(1, username);
		pstInsert.setString(2, password);
		pstInsert.execute();
	}

	public void delete(int id) throws SQLException {
		pstDelete.setInt(1, id);
		pstDelete.execute();
	}

	public void update(String username, String password) throws SQLException {
		pstUpdate.setString(1, username);
		pstUpdate.setString(2, password);
		pstUpdate.execute();
	}

	public ArrayList<User> selectAll() throws SQLException {

		ArrayList<User> users = new ArrayList<User>();

		ResultSet result = pstSelectAll.executeQuery();
		while (result.next()) {
			User user = new User();
			user.setId(result.getInt("id"));
			user.setUsername(result.getString("username"));
			user.setPassword("");
			users.add(user);
		}

		return users;
	}

	public User selectWhere(String username) throws SQLException {

		User user = null;
		pstSelectWhere.setString(1, username);

		ResultSet result = pstSelectWhere.executeQuery();
		if (result.next()) {
			user = new User();
			user.setId(result.getInt("id"));
			user.setUsername(result.getString("username"));
			user.setPassword(result.getString("password"));
		}	

		return user;
	}
}
