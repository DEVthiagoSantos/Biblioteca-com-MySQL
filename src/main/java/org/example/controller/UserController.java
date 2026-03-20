package org.example.controller;

import org.example.model.UserModel;
import org.example.service.UserService;

import java.sql.SQLException;
import java.util.List;

public record UserController(UserService userService) {

    public void createUser(String userName, String email) throws SQLException {

        userService.insert(userName, email);
    }

    public List<UserModel> getAllUsers() throws SQLException {

        return userService.list();
    }

    public UserModel searchUserById(int idUser) throws SQLException {

        return userService.listId(idUser);
    }

    public List<UserModel> searchUsersByName(String userName) throws SQLException {

        return userService.searchNames(userName);
    }

    public void updateUser(String userName, String email, int idUser) throws SQLException {

        userService.update(userName, email, idUser);
    }

    public void depositBalance(double value, int idUser) throws SQLException {

        userService.depositBalance(value, idUser);
    }

    public void deleteUser(int idUser) throws SQLException {

        userService.deleteUser(idUser);
    }
}
