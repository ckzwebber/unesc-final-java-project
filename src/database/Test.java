package database;

import java.sql.Connection;
import java.util.ArrayList;

import database.dao.UserDAO;
import database.model.User;
import io.github.cdimascio.dotenv.Dotenv;

public class Test {

	public static void main(String[] args) {
		
		 Dotenv dotenv = Dotenv.load();
		 String connectionString = dotenv.get("DB_CONNECTION_STRING");
		
		try {
			Connection conn = ConnectionFactory.getConnection(connectionString);
			if (conn != null) {
				System.out.println("Conectou!!!!");
				
				UserDAO dao = new UserDAO(conn);
			
				ArrayList<User> list = dao.selectAll();
				
				for (User u : list) {
					System.out.println(u.getUser());
				} 
				
				/*
				dao.insert("username", "password");
				dao.delete("username");
				dao.select();
				*/
				
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}

		
	}

}
