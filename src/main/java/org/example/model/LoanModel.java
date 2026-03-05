package org.example.model;

import org.example.Enum.Status;

import java.time.LocalDateTime;

public class LoanModel {

    private int idLoan;
    private int idUser;
    private int idBook;
    private LocalDateTime loanDate;
    private LocalDateTime expected_return_date;
    private LocalDateTime actual_return_date;
    private Status status;

    public LoanModel() {}

    public LoanModel(int id_user, int id_book, LocalDateTime expected_return_date) {
        this.idUser = id_user;
        this.idBook = id_book;
        this.expected_return_date = expected_return_date;
    }

    public LoanModel(int id_user, int id_book, LocalDateTime expected_return_date, int id_loan) {
        this.idUser = id_user;
        this.idBook = id_book;
        this.idLoan = id_loan;
        this.expected_return_date = expected_return_date;
    }

    public LoanModel(int idLoan, int idUser,
                     int idBook, LocalDateTime loanDate,
                     LocalDateTime expected_return_date,
                     LocalDateTime actual_return_date,
                     Status status) {
        this.idLoan = idLoan;
        this.idUser = idUser;
        this.idBook = idBook;
        this.loanDate = loanDate;
        this.expected_return_date = expected_return_date;
        this.actual_return_date = actual_return_date;
        this.status = status;
    }

    public int getIdLoan() {
        return idLoan;
    }

    public void setIdLoan(int idLoan) {
        this.idLoan = idLoan;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDateTime getExpected_return_date() {
        return expected_return_date;
    }

    public void setExpected_return_date(LocalDateTime expected_return_date) {
        this.expected_return_date = expected_return_date;
    }

    public LocalDateTime getActual_return_date() {
        return actual_return_date;
    }

    public void setActual_return_date(LocalDateTime actual_return_date) {
        this.actual_return_date = actual_return_date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
