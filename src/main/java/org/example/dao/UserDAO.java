package org.example.dao;


import org.example.connection.Connect;
import org.example.model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    //CREATE
    public void InsertUser(UserModel userModel) throws SQLException {

        String sql = "INSERT INTO users (user_name, email) VALUES (?, ?)";

        try (Connection conn = Connect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userModel.getUser_name());
            stmt.setString(2, userModel.getEmail());
            stmt.executeUpdate();

        }
    }

    //ROAD
    public List<UserModel> ListUsers() throws SQLException {

        String sql = "SELECT * FROM users";
        List<UserModel> list = new ArrayList<>();

        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    list.add(User(rs));
                }
            }
        }

        return list;
    }

    // ROAD - ID
    public List<UserModel> searchById(int id) throws SQLException {

        String sql = "SELECT * FROM users WHERE id_user = ?";
        List<UserModel> user = new ArrayList<>();

        try (Connection conn = Connect.connect();
           PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    user.add(User(rs));
                    return user;

                }
            }
        }
        return null;
    }

    // List Names
    public List<UserModel> listNames(String name) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_name LIKE ?";
        List<UserModel> lista = new ArrayList<>();
        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name + "%");
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    lista.add(User(rs));
                }
            }
        }

        return lista;
    }

    // UPDATE
    public void UserUpdate(UserModel user) throws SQLException {

        String sql = "UPDATE users SET user_name =?, email =? WHERE id_user =?";

        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUser_name());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getId_user());
            stmt.executeUpdate();

        }
    }

    // DELETE
    public void DeleteUser(int id) throws SQLException {

        String sql = "DELETE FROM users WHERE id_user = ?";

        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // List Users
    public static UserModel User(ResultSet rs) throws SQLException {

        UserModel user = new UserModel();
        user.setId_user(rs.getInt("id_user"));
        user.setUser_name(rs.getString("user_name"));
        user.setEmail(rs.getString("email"));

        return user;
    }
}
