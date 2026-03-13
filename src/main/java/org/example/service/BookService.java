package org.example.service;

import org.example.dao.BookDAO;
import org.example.model.BookModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BookService {

    BookDAO bookDao = new BookDAO();

    // Insert book
    public void insertBook(String author, String title
            , int total_quantity, int quantity_available) throws SQLException {

        verification(author, title, total_quantity, quantity_available);

        BookModel bookModel = new BookModel(author, title, total_quantity, quantity_available);
        bookDao.insert(bookModel);
    }

    // Update book
    public void updateBook(String author, String title,
                           int total_quantity, int quantity_available, int idBook) throws SQLException {

        verification(author, title, total_quantity, quantity_available);
        if (bookDao.searchID(idBook) == null) {
            throw new RuntimeException("Book not found in the library.");
        }

        BookModel bookModel = new BookModel(author, title,
                total_quantity, quantity_available, idBook);
        bookDao.update(bookModel);
    }

    // SEARCH - AUTHOR
    public Map<String, Integer> searchBookByAuthor(String author) throws SQLException {

        Map<String, Integer> mapa = bookDao.searchBookByAuthor(author);

        if (mapa.isEmpty()) {
            throw new RuntimeException("There is no information about is author.");
        }

        return mapa;
    }

    // ROAD
    public List<BookModel> listBook() throws SQLException {

        if (bookDao.list() == null) {
            throw new RuntimeException("The are no books on the shelf.");
        }

        return bookDao.list();
    }

    // ROAD - ID
    public BookModel listBookID(int idBook) throws SQLException {

        if (idBook <= 0) {
            throw new RuntimeException("ID cannot be less than or equal to 0.");
        } else if (bookDao.searchID(idBook) == null) {
            throw new RuntimeException("This book is not on the shelf");
        }

        return bookDao.searchID(idBook);
    }

    // AUTHOR
    public BookModel bookAuthor(String author) throws SQLException {

        if (!author.matches("^[a-zA-ZÀ-ÿ ]+$")) {
            throw new RuntimeException("The author cannot have numbers in their name.");
        } else if (bookDao.listAuthor(author) == null) {
            throw new RuntimeException("This author is not on the shelf");
        }

        return bookDao.listAuthor(author);
    }

    // TITLE
    public BookModel bookTitle(String title) throws SQLException {

        if (title.isBlank()) {
            throw new RuntimeException("The title cannot be blank.");
        } else if (bookDao.searchTitle(title) == null) {
            throw new RuntimeException("This title is not on the shelf");
        }

        return bookDao.searchTitle(title);
    }

    // Delete
    public void deleteBook(int id) throws SQLException {

        if (id <= 0) {
            throw new RuntimeException("ID cannot be less than or equal to 0.");
        } else if (bookDao.searchID(id) == null) {
            throw new RuntimeException("This book is not on the shelf");
        }

        bookDao.delete(id);
    }

    public void verification(String author, String title
            , int total_quantity, int quantity_available) throws SQLException {

        if (!author.matches("^[a-zA-ZÀ-ÿ. ]+$") || author.isBlank()) {
            throw new RuntimeException("The author's name is invalid");
        } else if (title.isBlank()) {
            throw new RuntimeException("The title cannot be blank");
        } else if (total_quantity < 0 || quantity_available < 0) {
            throw new RuntimeException("Total quantity and Quantity Available cannot be less than 0");
        }

    }
}
