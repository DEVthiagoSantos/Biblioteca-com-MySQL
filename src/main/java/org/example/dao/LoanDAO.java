package org.example.dao;

import org.example.Enum.Status;
import org.example.connection.Connect;
import org.example.model.BookModel;
import org.example.model.LoanModel;
import org.example.model.UserModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LoanDAO {

    public void insert(Connection conn, LoanModel loanModel) throws SQLException {

        String sql = "INSERT INTO loans (id_user, id_book, expected_return_date) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                loanModel.setIdLoan(keys.getInt(1));
            }
            stmt.setInt(1, loanModel.getUser().getIdUser());
            stmt.setInt(2, loanModel.getBook().getIdBook());
            stmt.setTimestamp(3, Timestamp.valueOf(loanModel.getExpected_return_date()));
            stmt.executeUpdate();

        }
    }

    public void update(int idUser, int idBook, int idLoan) throws SQLException {
        String sql = """
                UPDATE loans SET id_user=?, id_book=?, loan_date=?, expected_return_date=?
                WHERE id_loan=?""";

        try (Connection conn = Connect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            stmt.setInt(2, idBook);

            LocalDateTime loanDate = LocalDateTime.now();
            LocalDateTime dateExpectedReturn = LocalDateTime.now().plusDays(7);
            stmt.setTimestamp(3, Timestamp.valueOf(loanDate));
            stmt.setTimestamp(4, Timestamp.valueOf(dateExpectedReturn));

            stmt.setInt(5, idLoan);

            stmt.executeUpdate();
        }
    }

    public void updateReturnDate(Connection conn, int id) throws SQLException {

        String sql = """
                UPDATE loans
                SET actual_return_date = NOW(),
                    current_status = 'RETURNED'
                WHERE id_loan = ?""";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void deleteLoan(int idLoan) throws SQLException {

        String sql = "DELETE FROM loans WHERE id_loan = ?";

        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idLoan);
            stmt.executeUpdate();
        }
    }

    public LoanModel findActiveLoan(int idUser, int idBook) throws SQLException {

        String sql = """
                SELECT * FROM loans
                WHERE id_user = ? AND id_book = ? AND current_status = 'LOANED'
                """;

        return returnLoan(idUser, idBook, sql);
    }

    public LoanModel findLoan(int idUser, int idBook) throws SQLException {

        String sql = """
                SELECT * FROM loans
                WHERE id_user = ? AND id_book = ?
                """;

        return returnLoan(idUser, idBook, sql);
    }

    public List<LoanModel> findLoansByUser(int idUser) throws SQLException {

        String sql = """
                SELECT loans.id_loan AS ID_Loan,
                       users.id_user AS id_user,
                       users.user_name AS user_name,
                       books.id_book AS id_book,
                       books.title AS title,
                       books.author AS author,
                       loans.loan_date AS loan_date,
                       loans.expected_return_date AS return_date,
                       loans.actual_return_date AS actual_return_date,
                       loans.current_status AS status
                FROM loans
                INNER JOIN users ON loans.id_user = users.id_user
                INNER JOIN books ON loans.id_book = books.id_book
                WHERE users.id_user = ?""";
        List<LoanModel> list = new ArrayList<>();

        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUser);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    list.add(mapLoan(rs));
                    return list;
                }
            }
        }

        return null;
    }

    public LoanModel searchLoan(int idLoan) throws SQLException {

        String sql = """
                SELECT loans.id_loan AS ID_Loan,
                       users.id_user AS id_user,
                       users.user_name AS user_name,
                       books.id_book AS id_book,
                       books.title AS title,
                       books.author AS author,
                       loans.loan_date AS loan_date,
                       loans.expected_return_date AS return_date,
                       loans.actual_return_date AS actual_return_date,
                       loans.current_status AS status
                FROM loans
                INNER JOIN users ON loans.id_user = users.id_user
                INNER JOIN books ON loans.id_book = books.id_book
                WHERE loans.id_loan = ?""";

        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idLoan);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return mapLoan(rs);
                }
            }
        }

        return null;
    }

    public List<LoanModel> readLoan() throws SQLException {

        String sql = """
                SELECT loans.id_loan AS ID_Loan,
                       users.id_user AS id_user,
                       users.user_name AS user_name,
                       books.id_book AS id_book,
                       books.title AS title,
                       books.author AS author,
                       loans.loan_date AS loan_date,
                       loans.expected_return_date AS return_date,
                       loans.actual_return_date AS actual_return_date,
                       loans.current_status AS status
                FROM loans
                INNER JOIN users ON loans.id_user = users.id_user
                INNER JOIN books ON loans.id_book = books.id_book""";

        List<LoanModel> list = new ArrayList<>();

        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    list.add(mapLoan(rs));
                }
            }
        }
        return list;
    }

    // Contagem total de quantos usuarios tem em cada livro
    public Map<String, Integer> countNumberLoans() throws SQLException {

        String sql = """
                SELECT books.title AS title,
                       COUNT(loans.id_user) AS users
                FROM loans
                INNER JOIN users ON loans.id_user = users.id_user
                INNER JOIN books ON loans.id_book = books.id_book
                GROUP BY books.title""";
        Map<String, Integer> mapa = new LinkedHashMap<>();

        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    mapa.put(
                          rs.getString("title"),
                          rs.getInt("users")
                    );

                }
            }
        }

        return mapa;
    }

    public static LoanModel mapLoan(ResultSet rs) throws SQLException {

        LocalDateTime loanDate = rs.getTimestamp("loan_date").toLocalDateTime();
        LocalDateTime dateReturn = rs.getTimestamp("return_date").toLocalDateTime();
        Timestamp actualTimestamp = rs.getTimestamp("actual_return_date");
        LocalDateTime actualDate = actualTimestamp != null ? actualTimestamp.toLocalDateTime() : null;

        UserModel userModel = new UserModel();
        userModel.setIdUser(rs.getInt("id_user"));
        userModel.setUserName(rs.getString("user_name"));

        BookModel bookModel = new BookModel();
        bookModel.setIdBook(rs.getInt("id_book"));
        bookModel.setTitle(rs.getString("title"));
        bookModel.setAuthor(rs.getString("author"));

        LoanModel loanModel = new LoanModel();
        loanModel.setIdLoan(rs.getInt("ID_Loan"));
        loanModel.setLoanDate(loanDate);
        loanModel.setExpected_return_date(dateReturn);
        loanModel.setActual_return_date(actualDate);
        loanModel.setBook(bookModel);
        loanModel.setUser(userModel);
        loanModel.setStatus(Status.valueOf(rs.getString("status")));

        return loanModel;
    }

    public static LoanModel returnLoan(int idUser, int idBook, String sql) throws SQLException {

        try (Connection conn = Connect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            stmt.setInt(2, idBook);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    LoanModel loan = new LoanModel();
                    loan.setIdLoan(rs.getInt("id_loan"));

                    return loan;
                }
            }
        }

        return null;
    }

}
