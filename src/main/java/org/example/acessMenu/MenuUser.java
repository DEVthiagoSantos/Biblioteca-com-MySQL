package org.example.acessMenu;

import org.example.Enum.UserMenu;
import org.example.controller.BookController;
import org.example.controller.LoanController;
import org.example.controller.UserController;
import org.example.model.BookModel;
import org.example.model.LoanModel;
import org.example.model.UserModel;
import org.example.service.BookService;
import org.example.service.LoanService;
import org.example.service.UserService;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

public class MenuUser {

    private static int userModel;

    public MenuUser(int userModel) {
        this.userModel = userModel;
    }

    static UserService userService = new UserService();
    static BookService bookService = new BookService();
    static LoanService loanService = new LoanService();
    static UserController users = new UserController(userService);
    static BookController books = new BookController(bookService);
    static LoanController loans = new LoanController(loanService);


    public static Scanner input = new Scanner(System.in);

    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    static void linha() {
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }

    static String returnActualDate(LoanModel data) throws SQLException {

        if (data.getActual_return_date() == null) {
            return "Ainda não retornado.";
        } else {
            return data.getActual_return_date().format(formatter);
        }
    }

    static void getLoan(LoanModel loan) throws SQLException {

        String data = returnActualDate(loan);
        String resultLoans = """
                        ID: %d
                        Usuário: %s
                        Livro: %s
                        Data do Empréstimo: %s
                        Data Limite de Retorno: %s
                        Data de Retorno: %s
                        Status: %s""".formatted(loan.getIdLoan(),
                loan.getUser().getUserName(), loan.getBook().getTitle(),
                loan.getLoanDate().format(formatter),
                loan.getExpected_return_date().format(formatter),
                data,
                loan.getStatus());
        linha();
        System.out.println(resultLoans);
    }

    public static UserMenu menuUsers() throws SQLException {

        String menuUser = """
                ============== Menu ================
                [ 1 ] Visualizar Usuário
                [ 2 ] Listar Empréstimos
                [ 3 ] Atualizar Usuário
                [ 4 ] Buscar Livro (ID)
                [ 5 ] Buscar Livros (Nome)
                [ 6 ] Listar Todos os Livros
                [ 7 ] Fazer Empréstimo
                [ 8 ] Retornar Empréstimo
                [ 9 ] Sair""";

        System.out.println(menuUser);

        System.out.print("Escolha: ");
        int opcao = Integer.parseInt(input.nextLine());

        return executarUsers(opcao);
    }

    public static UserMenu executarUsers(int opcao) throws SQLException {

        switch (opcao) {

            case 1 :
                searchUserByID();
                break;
            case 2 :
                getLoanByUser();
                break;
            case 3 :
                updateUser();
                break;
            case 4 :
                getBookByID();
                break;
            case 5 :
                getBooksByTitle();
                break;
            case 6 :
                getAllBooks();
                break;
            case 7 :
                createLoan();
                break;
            case 8 :
                returnLoan();
                break;
            case 9 :
                return UserMenu.SAIR;
        }

        return UserMenu.USERS;
    }

    // Metodos do Usuario

    public static void updateUser() throws SQLException {

        try {
            System.out.print("Nome do Usuário: ");
            String nome = input.nextLine();
            System.out.print("Email: ");
            String email = input.nextLine();
            users.updateUser(nome, email, userModel);

            System.out.println("Usuário atualizado");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void searchUserByID() throws SQLException {

        try {

            UserModel user = users.searchUserById(userModel);
            String resultUser = """
                    ID: %d
                    Nome: %s
                    Email: %s""".formatted(user.getIdUser(),
                    user.getUserName(), user.getEmail());
            linha();
            System.out.println(resultUser);

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    // METODOS DOS LIVROS

    public static void getAllBooks() throws SQLException {

        try {

            for (BookModel book : books.getAllBooks()) {

                linha();
                String resultBook = """
                    ID: %d
                    Titulo: %s
                    Autor: %s
                    Quantidade Total: %d
                    Quantidade Disponível: %d""".formatted(book.getIdBook(),
                        book.getTitle(), book.getAuthor(), book.getTotalQuantity(),
                        book.getQuantityAvailable());
                System.out.println(resultBook);

            }

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public static void getBookByID() throws SQLException {

        try {
            System.out.println("Digite o ID do livro");
            System.out.print("ID: ");
            int idBook = Integer.parseInt(input.nextLine());

            BookModel bookModel = books.getBookById(idBook);
            linha();
            String bookResult = """
                    ID: %d
                    Titulo: %s
                    Autor: %s
                    Quantidade Total: %d
                    Quantidade Disponível: %d""".formatted(bookModel.getIdBook(),
                    bookModel.getTitle(), bookModel.getAuthor(), bookModel.getTotalQuantity(),
                    bookModel.getTotalQuantity());
            System.out.println(bookResult);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getBooksByTitle() throws SQLException {

        try {

            System.out.print("Titulo do Livro: ");
            String author = input.nextLine();

            Map<String, Integer> mapa = books.searchBooksByTitle(author);

            for (Map.Entry<String, Integer> entry : mapa.entrySet()){

                System.out.println("Titulo: " + entry.getKey());
                System.out.println("ID: " + entry.getValue());

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Metodos de empréstimo
    public static void createLoan() throws SQLException {

        try {

            UserModel user = users.searchUserById(userModel);
            System.out.print("Titulo do Livro: ");
            String titulo = input.nextLine();

            loans.createLoan(user.getUserName(), titulo);
            System.out.println("Empréstimo criado.");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public static void returnLoan() throws SQLException {

        try {

            UserModel user = users.searchUserById(userModel);
            System.out.print("Livro: ");
            String livro = input.nextLine();

            loans.returnLoan(user.getUserName(), livro);
            System.out.println("Livro retornado.");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    public static void getLoanByUser() throws SQLException {

        try {

            for (LoanModel loan : loans.getLoanByUser(users.searchUserById(userModel)
                    .getUserName())) {

                getLoan(loan);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void runSystem() {

        UserMenu userMenu = UserMenu.USERS;

        while (userMenu != UserMenu.SAIR) {

            try {

                userMenu = switch (userMenu) {

                    case USERS -> menuUsers();
                    default -> UserMenu.SAIR;

                };

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
