package org.example.model;

import org.example.Enum.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LoanModel {

    private int idLoan;
    private UserModel user;
    private BookModel book;
    private LocalDateTime loanDate;
    private LocalDateTime expected_return_date;
    private LocalDateTime actual_return_date;
    private Status status;

    public LoanModel() {}

    public LoanModel(UserModel id_user, BookModel id_book, LocalDateTime expected_return_date) {
        this.user = id_user;
        this.book = id_book;
        this.expected_return_date = expected_return_date;
    }

    public LoanModel(UserModel id_user, BookModel id_book, LocalDateTime expected_return_date, int id_loan) {
        this.user = id_user;
        this.book = id_book;
        this.idLoan = id_loan;
        this.expected_return_date = expected_return_date;
    }

    public LoanModel(int idLoan, UserModel idUser,
                     BookModel idBook, LocalDateTime loanDate,
                     LocalDateTime expected_return_date,
                     LocalDateTime actual_return_date,
                     Status status) {
        this.idLoan = idLoan;
        this.user = idUser;
        this.book = idBook;
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

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public BookModel getBook() {
        return book;
    }

    public void setBook(BookModel book) {
        this.book = book;
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
