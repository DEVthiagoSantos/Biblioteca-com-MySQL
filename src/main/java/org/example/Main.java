package org.example;

import org.example.controller.BookController;
import org.example.controller.UserController;
import org.example.model.BookModel;
import org.example.model.UserModel;
import org.example.service.BookService;
import org.example.service.UserService;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class Main {

    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    static void linha() {
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }


    public static void main(String[] args) throws SQLException {

        UserService userService = new UserService();
        BookService bookService = new BookService();
        UserController users = new UserController(userService);
        BookController books = new BookController(bookService);

        for (BookModel book : books.searchByAuthor("J")) {
            System.out.println(book.getTitle() + " - " + book.getAuthor());
        }

    }

}