package org.example.controller;

import org.example.model.BookModel;
import org.example.service.BookService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void insertBook(String author, String title,
                           int totalQuantity, int quantityAvailable) throws SQLException {

        bookService.insertBook(author, title, totalQuantity, quantityAvailable);
    }

    public void updateBook(String author, String title,
                           int totalQuantity, int quantityAvailable, int idBook) throws SQLException {

        bookService.updateBook(author, title, totalQuantity, quantityAvailable, idBook);
    }

    public List<BookModel> getAllBooks() throws SQLException {

        return bookService.listBook();
    }

    public BookModel getBookById(int idBook) throws SQLException {

        return bookService.listBookID(idBook);
    }

    public Map<String, Integer> searchBooksByTitle(String title) throws SQLException {

        return bookService.searchBookByTitle(title);
    }

    public void deleteBookById(int idBook) throws SQLException {

        bookService.deleteBook(idBook);
    }
}
