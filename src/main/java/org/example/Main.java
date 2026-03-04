package org.example;

import org.example.dao.BookDAO;
import org.example.model.BookModel;
import org.example.service.UserService;

import java.sql.SQLException;

public class Main {


    public static void main(String[] args) throws SQLException {

        UserService userService = new UserService();
        BookDAO bookDAO = new BookDAO();
        for (BookModel books : bookDAO.listBooks()) {
            System.out.println(books.getAuthor() + " | " + books.getTitle());
        }
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println("Livro de ID: 3");
        for (BookModel books : bookDAO.listBookID(3)) {
            System.out.println(books.getAuthor() + " | " + books.getTitle());
        }

    }

}