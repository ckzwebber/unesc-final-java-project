package database;

import java.sql.Connection;
import java.util.ArrayList;
import database.dao.UserDAO;
import database.model.User;
import io.github.cdimascio.dotenv.Dotenv;
import services.ImportService;
import utils.CourseInfo;

public class Test {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();
		String connectionString = dotenv.get("DB_CONNECTION_STRING");

		CourseInfo test = ImportService.readImportFile();
		System.out.println(test.courseInfoToString());
	}
}


/*	try {
			Connection conn = ConnectionFactory.getConnection(connectionString);
			if (conn != null) {
				System.out.println("Conectou!!!!");
				
				UserDAO dao = new UserDAO(conn);
			
				ArrayList<User> list = dao.selectAll();
				
				for (User u : list) {
					System.out.println(u.getUser());
				} 
				dao.insert("username", "password");
				dao.delete("username");
				dao.select();
				
				
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
*/