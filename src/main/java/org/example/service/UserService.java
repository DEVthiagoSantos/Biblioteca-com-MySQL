package org.example.service;

import org.example.dao.UserDAO;
import org.example.model.UserModel;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    UserDAO userDAO = new UserDAO();

    // Inserir usuario
    public void insertUser(String nome, String email) throws SQLException {

        nome = nome.trim();
        email = email.trim();

        if (!nome.matches("^[a-zA-ZÀ-ÿ ]+$") || nome.isBlank()) {
            throw new RuntimeException("Invalid username");
        }
        if (!email.matches("^[^@\\s]+@[a-zA-Z0-9-]+\\.com$") || email.isBlank()) {
            throw new RuntimeException("Invalid user email address");
        }

        UserModel userModel = new UserModel(nome, email);
        userDAO.InsertUser(userModel);
    }

    // Listar todos os usuarios
    public List<UserModel> listUsers() throws SQLException {

        if (userDAO.ListUsers() == null) {
            throw new RuntimeException("Not users in list");
        }

        return userDAO.ListUsers();
    }

    // Listar o usuario pelo id dele
    public List<UserModel> listIdUser(int id) throws SQLException {

        if (userDAO.searchById(id) == null) {
            throw new RuntimeException("User not found");
        }

        return userDAO.searchById(id);
    }

    // Listar nomes dos usuarios
    public List<UserModel> listUserName(String nome) throws SQLException {

        nome = nome.trim();
        if (nome.isBlank() || !nome.matches("^[a-zA-ZÀ-ÿ ]+$")) {
            throw new RuntimeException("Error! name is invalid");
        }

        return userDAO.listNames(nome);
    }

    // Atualizar o usuario
    public void updateUser(String name, String email, int idUser) throws SQLException {

        name = name.trim();
        email = email.trim();

        if (!name.matches("^[a-zA-ZÀ-ÿ ]+$") || name.isBlank()) {
            throw new RuntimeException("Invalid name and cannot be blank");
        }
        if (!email.matches("^[^@\\s]+@[a-zA-Z0-9-]+\\.com$")
                || email.isBlank()) {
            throw new RuntimeException("Invalid email and cannot be blank");
        }
        if (userDAO.searchById(idUser) == null) {
            throw new RuntimeException("User not found");
        }

        UserModel userModel = new UserModel(name, email, idUser);
        userDAO.UserUpdate(userModel);
    }

    // Deletar um usuario do banco de dados
    public void deleteUser(int id) throws SQLException {

        if (userDAO.searchById(id) == null) {
            throw new RuntimeException("User not found, it is not possible to delete");
        }

        userDAO.DeleteUser(id);
    }


}
