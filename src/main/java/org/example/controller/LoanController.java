package org.example.controller;

import org.example.model.LoanModel;
import org.example.service.LoanService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    public void createLoan(String userName, String bookTitle) throws SQLException {

        loanService.createLoan(userName, bookTitle);
    }

    public void returnLoan(String userName, String bookTitle) throws SQLException {

        loanService.returnLoan(userName, bookTitle);
    }

    public void updateLoan(String userName, String bookTitle, int idLoan) throws SQLException {

        loanService.updateLoan(userName, bookTitle, idLoan);
    }

    public List<LoanModel> getAllLoans() throws SQLException {

        return loanService.getAllLoans();
    }

    public Map<String, Integer> getCountLoans() throws SQLException {

        return loanService.countLoans();
    }

    public void deleteLoan(String userName, String bookTitle) throws SQLException {

        loanService.deleteLoan(userName, bookTitle);
    }

}
