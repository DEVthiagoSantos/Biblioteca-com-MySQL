package org.example;

import org.example.controller.BookController;
import org.example.controller.LoanController;
import org.example.controller.UserController;
import org.example.model.LoanModel;
import org.example.service.BookService;
import org.example.service.LoanService;
import org.example.service.UserService;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Main {

    UserService userService = new UserService();
    BookService bookService = new BookService();
    LoanService loanService = new LoanService();
    UserController users = new UserController(userService);
    BookController books = new BookController(bookService);
    LoanController loans = new LoanController(loanService);

    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    static void linha() {
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }


    public static void main(String[] args) throws SQLException {




    }

}