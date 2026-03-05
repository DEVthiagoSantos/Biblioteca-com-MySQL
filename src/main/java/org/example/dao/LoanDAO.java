package org.example.dao;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import org.example.connection.Connect;
import org.example.model.LoanModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class LoanDAO {

    public void insert(LoanModel loanModel) throws SQLException {

        String sql = "INSERT INTO loans (id_user, id_book, expected_return_date) VALUES (?, ?, ?)";

        try (Connection conn = Connect.connect();

             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, loanModel.getIdUser());
            stmt.setInt(2, loanModel.getIdBook());
            stmt.setTimestamp(3, Timestamp.valueOf(loanModel.getExpected_return_date()));
            stmt.executeUpdate();

        }
    }

    public void update(LoanModel loanModel) throws SQLException {
        String sql = "UPDATE loans SET id_user=?, id_book=?, expected_return_date=? WHERE id_loan=?";

        try (Connection conn = Connect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, loanModel.getIdUser());
            stmt.setInt(2, loanModel.getIdBook());
            stmt.setTimestamp(3, Timestamp.valueOf(loanModel.getExpected_return_date()));
            stmt.setInt(4, loanModel.getIdLoan());

            stmt.executeUpdate();
        }
    }


}
