package org.example;

import org.example.dao.BookDAO;
import org.example.model.BookModel;
import org.example.service.BookService;
import org.example.service.UserService;

import java.sql.SQLException;

public class Main {


    public static void main(String[] args) throws SQLException {

        UserService userService = new UserService();
        BookDAO bookDAO = new BookDAO();
        BookService bookService = new BookService();

        for (BookModel books : bookService.listBookAuthor("")) {
            System.out.println(books.getAuthor() + " | " + books.getTitle());
        }
    }

}