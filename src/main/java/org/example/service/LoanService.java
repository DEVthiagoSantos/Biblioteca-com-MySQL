package org.example.service;

import org.example.connection.Connect;
import org.example.dao.BookDAO;
import org.example.dao.LoanDAO;
import org.example.dao.UserDAO;
import org.example.model.BookModel;
import org.example.model.LoanModel;
import org.example.model.UserModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class LoanService {

    private final LoanDAO loanDAO = new LoanDAO();
    private final BookDAO bookDAO = new BookDAO();
    private final UserDAO userDAO = new UserDAO();


    public void createLoan(String userName, String bookTitle) throws SQLException {

        updateLateLoans();

        userName = clearSpaces(userName);
        bookTitle = clearSpaces(bookTitle);

        verification(userName, bookTitle);

        UserModel user = userDAO.searchName(userName);
        BookModel book = bookDAO.searchTitle(bookTitle);

        if (user == null) {
            throw new RuntimeException("User is not registered");
        } else if (book == null) {
            throw new RuntimeException("Book is not registered");
        } else if (book.getQuantityAvailable() <= 0) {
            throw new RuntimeException("Number of unavailable book");
        }

        if (loanDAO.findActiveExists(user.getIdUser(), book.getIdBook())) {
            throw new RuntimeException("This user already has this book.");
        }

        try (Connection conn = Connect.connect()) {

            conn.setAutoCommit(false);

            try {


                LocalDateTime dateTime = LocalDateTime.now().plusDays(7);
                LoanModel loanModel = new LoanModel(user, book, dateTime);

                loanDAO.insert(conn, loanModel);
                bookDAO.decreaseQuantity(conn, book.getIdBook());

                conn.commit();

            } catch (SQLException e) {

                conn.rollback();
                throw  e;

            }
        }
    }

    public void updateLateLoans() throws SQLException {

        Connection conn = Connect.connect();

        List<LoanModel> loanModel = loanDAO.findLateLoans(conn);

        for (LoanModel loan : loanModel) {

            loanDAO.updateStatusLate(loan.getIdLoan(), "LATE");
        }

    }

    public void returnLoan(String userName, String bookTitle) throws SQLException {

        updateLateLoans();

        userName = clearSpaces(userName);
        bookTitle = clearSpaces(bookTitle);

        verification(userName, bookTitle);

        UserModel user = userDAO.searchName(userName);
        BookModel book = bookDAO.searchTitle(bookTitle);

        verificationID(user, book);

        LoanModel loan = loanDAO.findActiveLoan(user.getIdUser(), book.getIdBook());

        if (loan == null) {
            throw new RuntimeException("This user did not borrow this book");
        }

        try (Connection conn = Connect.connect()) {

            conn.setAutoCommit(false);

            try {

                loanDAO.updateReturnDate(conn, loan.getIdLoan());
                bookDAO.increaseQuantity(conn, book.getIdBook());

                conn.commit();
            } catch (SQLException e) {

                conn.rollback();
                throw e;
            }
        }

    }

    public void deleteLoan(String userName, String bookTitle) throws SQLException {

        userName = clearSpaces(userName);
        bookTitle = clearSpaces(bookTitle);

        verification(userName, bookTitle);

        UserModel user = userDAO.searchName(userName);
        BookModel book = bookDAO.searchTitle(bookTitle);

        verificationID(user, book);

        LoanModel loan = loanDAO.findLoan(user.getIdUser(), book.getIdBook());

        if (loan == null) {
            throw new RuntimeException("There are no loans with this ID.");
        }

        loanDAO.deleteLoan(loan.getIdLoan());

    }

    // UPDATE LOAN
    public void updateLoan(String userName, String bookTitle, int idLoan) throws SQLException {

        userName = clearSpaces(userName);
        bookTitle = clearSpaces(bookTitle);

        verification(userName, bookTitle);

        UserModel user = userDAO.searchName(userName);
        BookModel book = bookDAO.searchTitle(bookTitle);

        verificationID(user, book);

        LoanModel loan = loanDAO.searchLoan(idLoan);

        if (loan == null) {
            throw new RuntimeException("This is loan not registered");
        }

        loanDAO.update(user.getIdUser(), book.getIdBook(), loan.getIdLoan());
    }

    public List<LoanModel> getAllLoans() throws SQLException {

        List<LoanModel> loan = loanDAO.readLoan();

        if (loan.isEmpty()) {
            throw new RuntimeException("There are no borrowed or returned books");
        }

        return loan;
    }

    public List<LoanModel> findLoansByUser(int idUser) throws SQLException {

        List<LoanModel> loan = loanDAO.findLoansByUser(idUser);

        if (loan.isEmpty()) {
            throw new RuntimeException("This user has no loans");
        }

        return loan;
    }

    public Map<String, Integer> countLoans() throws SQLException {

        Map<String, Integer> mapa = loanDAO.countNumberLoans();

        if (mapa.isEmpty()) {
            throw new RuntimeException("There are no loans for counting");
        }

        return mapa;
    }
    static void verification(String name, String title) {

        if (!name.matches("^[a-zA-ZÀ-ÿ. ]+$")) {
            throw new RuntimeException("Invalid username");
        } else if (title.isBlank()) {
            throw new RuntimeException("The title cannot be left blank");
        }

    }

    static void verificationID(UserModel user, BookModel book) {

        if (user == null) {
            throw new RuntimeException("User is not registered");
        } else if (book == null) {
            throw new RuntimeException("Book is not registered");
        }

    }

    static String clearSpaces(String name) {
        return name.trim();
    }
}
