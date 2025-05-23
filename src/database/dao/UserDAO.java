package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.model.User;

public class UserDAO {

	private String selectAll = "SELECT * FROM users";
	private String selectWhere = "SELECT * FROM users WHERE username = ?";
	private String insert = "INSERT INTO users(username, password) VALUES (?, ?)";
	private String update = "UPDATE users SET password = ? WHERE username = ?";
	private String delete = "DELETE FROM users WHERE username = ?";
	
	private PreparedStatement pstSelectAll;
	private PreparedStatement pstSelectWhere;
	private PreparedStatement pstInsert;
	private PreparedStatement pstUpdate;
	private PreparedStatement pstDelete;
	
	public UserDAO( Connection conn) throws SQLException {
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
	
	public void delete(String username) throws SQLException {
		pstDelete.setString(1, username);
		pstDelete.execute();
	}
	
	public void update(String username, String password) throws SQLException {
		pstUpdate.setString(1, username);
		pstUpdate.setString(2, password);
		pstUpdate.execute();
	}
	
	public ArrayList<User> selectAll() throws SQLException {
		
		ArrayList<User> local = new ArrayList<User>();
		
		ResultSet result = pstSelectAll.executeQuery();
		while ( result.next() ) {
			User u = new User();
			u.setId( result.getInt("id") );
			u.setUser( result.getString("username") );
			u.setPassword( result.getString("password") );
			local.add(u);
		}
		
		return local;
	}
	
public User selectWhere(String username) throws SQLException {
		
		User local = null;
		pstSelectWhere.setString(1, username);
		
		ResultSet result = pstSelectWhere.executeQuery();
		if ( result.next() ) {
			local = new User();
			local.setId( result.getInt("id") );
			local.setUser( result.getString("username") );
			local.setPassword( result.getString("password") );
		}
		
		return local;
	}
}











