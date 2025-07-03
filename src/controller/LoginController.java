// LoginController.java
package controller;

import database.model.User;

public class LoginController {
    
    // Simulação de autenticação
    public static User authenticate(String username, String password) {
        // Aqui você pode consultar o banco de dados. Exemplo fixo:
        if (username.equals("admin") && password.equals("1234")) {
            User user = new User();
            user.setUsername("Admin");
            user.setUsername(username);
            return user;
        }
        return null;
    }
}

