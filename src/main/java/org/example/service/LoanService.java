package org.example.service;

import org.example.Enum.Status;
import org.example.connection.Connect;
import org.example.dao.BookDAO;
import org.example.dao.LoanDAO;
import org.example.dao.UserDAO;
import org.example.model.BookModel;
import org.example.model.LoanModel;
import org.example.model.UserModel;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.SplittableRandom;

public class LoanService {

    private final LoanDAO loanDAO = new LoanDAO();
    private final BookDAO bookDAO = new BookDAO();
    private final UserDAO userDAO = new UserDAO();

    private final static BigDecimal DAYS_LATE_FEE = new BigDecimal(2);


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

                // Transactions
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

        LocalDateTime now = LocalDateTime.now();

        for (LoanModel loan : loanModel) {

            long lateDays = ChronoUnit.DAYS.between(loan.getExpected_return_date(), now);

            if (lateDays > 0) {

                BigDecimal value = new BigDecimal(lateDays)
                        .multiply(DAYS_LATE_FEE);

                loanDAO.updateStatusLate(conn, loan.getIdLoan(), value, Status.LATE);

            }

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

                BigDecimal lateFee = calculateLateFee(loan);

                if (lateFee.compareTo(BigDecimal.ZERO) > 0) {

                    if (user.getAvailableBalance().compareTo(lateFee) < 0) {

                        throw new RuntimeException("Insufficient funds to pay fine");

                    }

                    userDAO.depitBalance(conn, user.getIdUser(), lateFee);
                }

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

        updateLateLoans();

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

        updateLateLoans();

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

        updateLateLoans();

        List<LoanModel> loan = loanDAO.readLoan();

        if (loan.isEmpty()) {
            throw new RuntimeException("There are no borrowed or returned books");
        }

        return loan;
    }

    public List<LoanModel> findLoansByUser(String userName) throws SQLException {

        updateLateLoans();

        List<LoanModel> loan = loanDAO.findLoansByUser(userName);

        if (loan.isEmpty()) {
            throw new RuntimeException("This user has no loans");
        }

        return loan;
    }

    public LoanModel searchLoanId(int idLoan) throws SQLException {

        updateLateLoans();

        LoanModel loan = loanDAO.searchLoan(idLoan);

        if (loan == null) {
            throw new RuntimeException("This loan not registered");
        }

        return loan;
    }

    public Map<String, Integer> countLoans() throws SQLException {

        updateLateLoans();

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

    static BigDecimal calculateLateFee(LoanModel loan) {


        LocalDateTime expectedDate = loan.getExpected_return_date();

        if (expectedDate == null) {
            return BigDecimal.ZERO;
        }

        LocalDateTime now = LocalDateTime.now();

        if (!expectedDate.isBefore(now)) {
            return BigDecimal.ZERO;
        }

        long days = ChronoUnit.DAYS.between(expectedDate, now);

        days = Math.max(1, days);

        return DAYS_LATE_FEE.multiply(BigDecimal.valueOf(days));
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
