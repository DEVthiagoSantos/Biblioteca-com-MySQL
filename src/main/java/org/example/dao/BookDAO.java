package org.example.dao;

import com.mysql.cj.exceptions.ClosedOnExpiredPasswordException;
import org.example.connection.Connect;
import org.example.model.BookModel;
import org.example.model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // Insert
    public void insertBook(BookModel book) throws SQLException {
        String sql = """
                INSERT INTO books
                (author, title, total_quantity, quantity_available)
                VALUES (?, ?, ?, ?)""";

        try (Connection conn = Connect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getAuthor());
            stmt.setString(2, book.getTitle());
            stmt.setInt(3, book.getTotal_quantity());
            stmt.setInt(4, book.getQuantity_available());
            stmt.executeUpdate();

        }
    }

    // ROAD
    public List<BookModel> listBooks() throws SQLException {
        String sql = "SELECT * FROM books";
        List<BookModel> lista = new ArrayList<>();

        try (Connection conn = Connect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    lista.add(assembleList(rs));
                }
            }
        }

        return lista;
    }

    // ROAD - ID
    public List<BookModel> listBookID(int id) throws SQLException {
        String sql = "SELECT * FROM books WHERE id_book = ?";

        List<BookModel> lista = new ArrayList<>();

        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    lista.add(assembleList(rs));
                    return lista;
                }
            }
        }

        return null;
    }

    public static BookModel assembleList(ResultSet rs) throws SQLException {
        BookModel bookModel = new BookModel();
        bookModel.setId_book(rs.getInt("id_book"));
        bookModel.setAuthor(rs.getString("author"));
        bookModel.setTitle(rs.getString("title"));
        bookModel.setTotal_quantity(rs.getInt("total_quantity"));
        bookModel.setQuantity_available(rs.getInt("quantity_available"));

        return bookModel;
    }
}
