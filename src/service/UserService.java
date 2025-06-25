package service;

import database.model.User;

public class UserService {

    public boolean authenticate(String username, String password) {

        // Criar logica de autenticação
        return true;
    }

    public User registerUser(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be empty");
        }

        if (username.length() < 3 || password.length() < 8) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        // Ver se o usuario ja existe
        // Criar hash e salt para a senha

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

    public void updateUserProfile(int userId, String newUsername, String newPassword) {
        // Criar logica de atualização de perfil
    }
}
