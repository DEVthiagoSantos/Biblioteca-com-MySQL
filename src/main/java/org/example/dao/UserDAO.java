package org.example.dao;


import org.example.connection.Connect;
import org.example.model.UserModel;
import org.example.response.PageResponse;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    //CREATE
    public void insert(UserModel userModel) throws SQLException {

        String sql = "INSERT INTO users (user_name, email) VALUES (?, ?)";

        try (Connection conn = Connect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userModel.getUserName());
            stmt.setString(2, userModel.getEmail());
            stmt.executeUpdate();

        }
    }

    //ROAD
    public List<UserModel> list() throws SQLException {

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

    public long countUsers(Connection conn) throws SQLException {

        String sql = "SELECT COUNT(*) FROM users";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }

        return 0;
    }

    // ROAD - ID
    public UserModel searchId(int id) throws SQLException {

        String sql = "SELECT * FROM users WHERE id_user = ?";
        List<UserModel> user = new ArrayList<>();

        try (Connection conn = Connect.connect();
           PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return User(rs);

                }
            }
        }
        return null;
    }

    // List Names
    public List<UserModel> searchNames(String name) throws SQLException {

        String sql = "SELECT * FROM users WHERE user_name LIKE ?";
        List<UserModel> listNames = new ArrayList<>();

        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name + "%");
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    listNames.add(User(rs));
                }
            }
        }

        return listNames;
    }

    public UserModel searchName(String name) throws SQLException {

        String sql = "SELECT * FROM users WHERE user_name LIKE ?";

        return returnUser(name, sql);
    }

    public UserModel searchEmail(String email) throws SQLException {

        String sql = "SELECT * FROM users WHERE email LIKE ?";

        return returnUser(email, sql);
    }

    // UPDATE
    public void update(UserModel user) throws SQLException {

        String sql = "UPDATE users SET user_name =?, email =? WHERE id_user =?";

        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getIdUser());
            stmt.executeUpdate();

        }
    }

    public void depitBalance(Connection conn,
                             int idUser,
                             BigDecimal lateFee) throws SQLException {

        String sql = "UPDATE users SET available_balance = ? WHERE id_user = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, lateFee);
            stmt.setInt(2, idUser);
            stmt.executeUpdate();
        }
    }

    public void depositBalance(BigDecimal value, int idUser) throws SQLException {

        String sql = "UPDATE users SET available_balance = ? WHERE id_user = ?";

        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, value);
            stmt.setInt(2, idUser);
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void delete(int id) throws SQLException {

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
        user.setIdUser(rs.getInt("id_user"));
        user.setUserName(rs.getString("user_name"));
        user.setEmail(rs.getString("email"));
        user.setAvailableBalance(rs.getBigDecimal("available_balance"));

        return user;
    }

    // Montagem
    public static UserModel returnUser(String string, String sql) throws SQLException {

        try (Connection conn = Connect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, string + "%");
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return User(rs);
                }
            }
        }

        return null;
    }
}
