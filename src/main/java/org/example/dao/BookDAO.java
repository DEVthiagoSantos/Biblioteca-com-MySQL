package org.example.dao;

import org.example.connection.Connect;
import org.example.model.BookModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // Insert
    public void insert(BookModel book) throws SQLException {
        String sql = """
                INSERT INTO books
                (author, title, totalQuantity, quantityAvailable)
                VALUES (?, ?, ?, ?)""";

        try (Connection conn = Connect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getAuthor());
            stmt.setString(2, book.getTitle());
            stmt.setInt(3, book.getTotalQuantity());
            stmt.setInt(4, book.getQuantityAvailable());
            stmt.executeUpdate();

        }
    }

    // ROAD
    public List<BookModel> list() throws SQLException {
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
    public List<BookModel> searchID(int id) throws SQLException {
        String sql = "SELECT * FROM books WHERE idBook = ?";

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

    // ROAD - Author
    public List<BookModel> listAuthor(String name) throws SQLException {

        String sql = "SELECT * FROM books WHERE author LIKE ?";

        return getBookModel(name, sql);
    }

    // ROAD - Title
    public List<BookModel> listTitle(String name) throws SQLException {

        String sql = "SELECT * FROM books WHERE title LIKE ?";

        return getBookModel(name, sql);
    }

    // UPDATE - ID
    public void update(BookModel bookModel) throws SQLException {

        String sql = """
                UPDATE books SET author =?,
                                 title =?,
                                 totalQuantity =?,
                                 quantityAvailable =? WHERE idBook =?""";

        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bookModel.getAuthor());
            stmt.setString(2, bookModel.getTitle());
            stmt.setInt(3, bookModel.getTotalQuantity());
            stmt.setInt(4, bookModel.getQuantityAvailable());
            stmt.setInt(5, bookModel.getIdBook());
            stmt.executeUpdate();
        }
    }

    // DELETE - ID
    public void delete(int id) throws SQLException {

        String sql = "DELETE FROM books WHERE idBook = ?";

        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public static List<BookModel> getBookModel(String name, String sql) throws SQLException {
        List<BookModel> list = new ArrayList<>();

        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name + "%");

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    list.add(assembleList(rs));
                }
            }
        }

        return list;
    }

    public static BookModel assembleList(ResultSet rs) throws SQLException {
        BookModel bookModel = new BookModel();
        bookModel.setIdBook(rs.getInt("idBook"));
        bookModel.setAuthor(rs.getString("author"));
        bookModel.setTitle(rs.getString("title"));
        bookModel.setTotalQuantity(rs.getInt("totalQuantity"));
        bookModel.setQuantityAvailable(rs.getInt("quantityAvailable"));

        return bookModel;
    }
}
