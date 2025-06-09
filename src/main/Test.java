package main;

import database.ConnectionFactory;
import view.ImportScreen;

public class Test {

	public static void main(String[] args) {
		
 	Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ConnectionFactory.closeConnection();
    }));

	new ImportScreen();
		
	}
}


/*	

CourseInfo test = ImportService.readImportFile();
System.out.println(test.courseInfoToString());

try {
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