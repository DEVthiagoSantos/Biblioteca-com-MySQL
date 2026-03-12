package org.example.dao;

import org.example.connection.Connect;
import org.example.model.BookModel;

import javax.xml.transform.Result;
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
                (author, title, total_quantity, quantity_available)
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
    public BookModel searchID(int id) throws SQLException {
        String sql = "SELECT * FROM books WHERE id_book = ?";

        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return assembleList(rs);

                }
            }
        }

        return null;
    }

    // ROAD - Author
    public BookModel listAuthor(String name) throws SQLException {

        String sql = "SELECT * FROM books WHERE author LIKE ?";
        return getBookModel(name, sql);
    }

    // ROAD - Title
    public BookModel searchTitle(String name) throws SQLException {

        String sql = "SELECT * FROM books WHERE title LIKE ?";

        return getBookModel(name, sql);
    }

    // UPDATE - ID
    public void update(BookModel bookModel) throws SQLException {

        String sql = """
                UPDATE books SET author =?,
                                 title =?,
                                 total_quantity =?,
                                 quantity_available =? WHERE id_book =?""";

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

    public void increaseQuantity(Connection conn, int idBook) throws SQLException {
        String sql = """
                UPDATE books
                SET quantity_available = quantity_available + 1
                WHERE id_book = ?""";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idBook);
            stmt.executeUpdate();
        }
    }

    public void decreaseQuantity(Connection conn, int idBook) throws SQLException {
        String sql = """
                UPDATE books
                SET quantity_available = quantity_available - 1
                WHERE id_book = ?""";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idBook);
            stmt.executeUpdate();
        }
    }

    // DELETE - ID
    public void delete(int id) throws SQLException {

        String sql = "DELETE FROM books WHERE id_book = ?";

        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public static BookModel getBookModel(String name, String sql) throws SQLException {


        try (Connection conn = Connect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name + "%");

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return assembleList(rs);
                }
            }
        }

        return null;
    }

    public static List<BookModel> getListBooks(String name, String sql) throws SQLException {
        List<BookModel> books = new ArrayList<>();

        try (Connection conn = Connect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name + "%");

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    books.add(assembleList(rs));
                }
            }
        }

        return books;
    }

    public static BookModel assembleList(ResultSet rs) throws SQLException {
        BookModel bookModel = new BookModel();
        bookModel.setIdBook(rs.getInt("id_book"));
        bookModel.setAuthor(rs.getString("author"));
        bookModel.setTitle(rs.getString("title"));
        bookModel.setTotalQuantity(rs.getInt("total_quantity"));
        bookModel.setQuantityAvailable(rs.getInt("quantity_available"));

        return bookModel;
    }
}
