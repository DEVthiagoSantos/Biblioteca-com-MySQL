package org.example;

import org.example.dao.LoanDAO;
import org.example.model.LoanModel;
import org.example.service.BookService;
import org.example.service.LoanService;
import org.example.service.UserService;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Main {

    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


    public static void main(String[] args) throws SQLException {

        UserService userService = new UserService();
        BookService bookService = new BookService();
        LoanService loanService = new LoanService();
        LoanDAO loanDAO = new LoanDAO();

        for (LoanModel loans : loanService.readLoan()) {
            System.out.println(loans.getUser().getUserName()
                    + " | " + loans.getBook().getTitle()
                    + " | " + loans.getLoanDate().format(formatter)
                    + " - " + loans.getExpected_return_date().format(formatter));
        }

        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        Map<String, Integer> mapa = loanService.countLoans();

        for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }

    }

}