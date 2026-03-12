package org.example.service;

import org.example.dao.UserDAO;
import org.example.model.UserModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class UserService {

    UserDAO userDAO = new UserDAO();

    // Inserir usuario
    public void insert(String nome, String email) throws SQLException {

        nome = nome.trim();
        email = email.trim();

        if (!nome.matches("^[a-zA-ZÀ-ÿ ]+$") || nome.isBlank()) {
            throw new RuntimeException("Invalid username");
        }
        if (!email.matches("^[^@\\s]+@[a-zA-Z0-9-]+\\.com$") || email.isBlank()) {
            throw new RuntimeException("Invalid user email address");
        }

        UserModel user = userDAO.searchEmail(email);

        if (user != null) {
            throw new RuntimeException("Email already exists.");
        }

        userDAO.insert(user);
    }

    // Listar todos os usuarios
    public List<UserModel> list() throws SQLException {

        if (userDAO.list() == null) {
            throw new RuntimeException("Not users in list");
        }

        return userDAO.list();
    }

    // Listar o usuario pelo id dele, uma forma mais precisa de achar o usuario
    public UserModel listId(int id) throws SQLException {

        if (userDAO.searchId(id) == null) {
            throw new RuntimeException("User not found");
        }

        return userDAO.searchId(id);
    }

    // Listar usuarios pelo nome
    public List<UserModel> searchNames(String nome) throws SQLException {

        nome = nome.trim();
        if (nome.isBlank() || !nome.matches("^[a-zA-ZÀ-ÿ ]+$")) {
            throw new RuntimeException("Error! name is invalid");
        }

        List<UserModel> users = userDAO.searchNames(nome);

        if (users.isEmpty()) {
            throw new RuntimeException("No users found.");
        }

        return users;
    }

    // Atualizar o usuario
    public void update(String name, String email, int idUser) throws SQLException {

        name = name.trim();
        email = email.trim();

        if (!name.matches("^[a-zA-ZÀ-ÿ ]+$") || name.isBlank()) {
            throw new RuntimeException("Invalid name and cannot be blank");
        }
        if (!email.matches("^[^@\\s]+@[a-zA-Z0-9-]+\\.com$")
                || email.isBlank()) {
            throw new RuntimeException("Invalid email and cannot be blank");
        }
        if (userDAO.searchId(idUser) == null) {
            throw new RuntimeException("User not found");
        }

        UserModel userModel = new UserModel(name, email, idUser);
        userDAO.update(userModel);
    }

    // Deletar um usuario do banco de dados
    public void deleteUser(int id) throws SQLException {

        if (userDAO.searchId(id) == null) {
            throw new RuntimeException("User not found, it is not possible to delete");
        }

        userDAO.delete(id);
    }


}
