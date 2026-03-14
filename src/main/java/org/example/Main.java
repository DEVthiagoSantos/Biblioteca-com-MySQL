package org.example;

import org.example.Enum.EstadoMenu;
import org.example.controller.BookController;
import org.example.controller.LoanController;
import org.example.controller.UserController;
import org.example.model.UserModel;
import org.example.service.BookService;
import org.example.service.LoanService;
import org.example.service.UserService;

import java.security.spec.RSAOtherPrimeInfo;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

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

    public static EstadoMenu menuUsers() throws SQLException {

        String menuUser = """
                ============== USERS ================
                [ 1 ] Cadastrar Usuário
                [ 2 ] Listar Usuários
                [ 3 ] Atualizar Usuários
                [ 4 ] Buscar Usuário (ID)
                [ 5 ] Buscar Usuários (Nome)
                [ 6 ] Books ->
                [ 7 ] Loans ->
                [ 8 ] Sair""";

        System.out.println(menuUser);

        System.out.print("Escolha: ");
        int opcao = Integer.parseInt(input.nextLine());

        return executarUsers(opcao);
    }

    public static EstadoMenu menuBooks() throws SQLException {

        String menuBooks = """
                ============== BOOKS ================
                [ 1 ] Cadastrar Livro
                [ 2 ] Listar Livros
                [ 3 ] Atualizar Livro
                [ 4 ] Buscar Livro (ID)
                [ 5 ] Buscar Livros (Nome)
                [ 6 ] <- Users
                [ 7 ] Loans ->
                [ 8 ] Sair""";

        System.out.println(menuBooks);

        System.out.print("Escolha: ");
        int opcao = Integer.parseInt(input.nextLine());

        return executarBooks(opcao);
    }

    public static EstadoMenu menuLoans() throws SQLException {

        String menuLoans = """
                ============== LOANS ================
                [ 1 ] Cadastrar Empréstimo
                [ 2 ] Listar Empréstimos
                [ 3 ] Atualizar Empréstimo
                [ 4 ] Buscar Empréstimo (ID)
                [ 5 ] Buscar Empréstimos (Nome)
                [ 6 ] <- Users
                [ 7 ] <- Books
                [ 8 ] Sair""";
        System.out.println(menuLoans);

        System.out.print("Escolha: ");
        int opcao = Integer.parseInt(input.nextLine());

        return executarLoan(opcao);
    }

    public static EstadoMenu executarUsers(int opcao) throws SQLException {

        switch (opcao) {

            case 1 :
                createUser();
                break;
            case 2 :
                getAllUsers();
                break;
            case 3 :
                updateUser();
                break;
            case 4 :
                searchUserByID();
                break;
            case 5 :
                searchUserByName();
                break;
            case 6 :
                return EstadoMenu.BOOKS;
            case 7 :
                return EstadoMenu.LOANS;
            case 8 :
                return EstadoMenu.SAIR;
        }

        return EstadoMenu.USERS;
    }

    public static EstadoMenu executarBooks(int opcao) throws SQLException {

        switch (opcao) {

            case 1 :
                // Cadastrar Livro
            case 2 :
                // Listar Livros
            case 3 :
                // Atualizar livros
            case 4 :
                // Buscar Livro (ID)
            case 5 :
                // Buscar Livros (Nome)
            case 6 :
                return EstadoMenu.USERS;
            case 7 :
                return EstadoMenu.LOANS;
            case 8 :
                return EstadoMenu.SAIR;
        }

        return EstadoMenu.BOOKS;
    }

    public static EstadoMenu executarLoan(int opcao) throws SQLException {

        switch (opcao) {

            case 1 :
                // Cadastrar Livro
            case 2 :
                // Listar Livros
            case 3 :
                // Atualizar livros
            case 4 :
                // Buscar Livro (ID)
            case 5 :
                // Buscar Livros (Nome)
            case 6 :
                return EstadoMenu.USERS;
            case 7 :
                return EstadoMenu.BOOKS;
            case 8 :
                return EstadoMenu.SAIR;
        }

        return EstadoMenu.LOANS;
    }

    // Metodos do Usuario
    public static void createUser() throws SQLException {

        try {

            System.out.print("Nome do Usuário:");
            String user = input.nextLine();
            System.out.print("Email: ");
            String email = input.nextLine();

            System.out.println("Usuário cadastrado com sucesso.");

            users.createUser(user, email);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getAllUsers() throws SQLException {

        try {

            System.out.println("Listando Usuarios: ");
            for (UserModel user : users.getAllUsers()) {
                linha();
                System.out.println("ID: " + user.getIdUser());
                System.out.println("Nome: " + user.getUserName());
                System.out.println("Email: " + user.getEmail());

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateUser() throws SQLException {

        try {
            System.out.println("Digite o ID do usuário que deseja editar: ");
            System.out.print("ID: ");
            int id = Integer.parseInt(input.nextLine());
            System.out.println("Mude o que deseja.");
            System.out.print("Nome do Usuário: ");
            String nome = input.nextLine();
            System.out.print("Email: ");
            String email = input.nextLine();
            users.updateUser(nome, email, id);

            System.out.println("Usuário atualizado");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void searchUserByID() throws SQLException {

        try {

            System.out.print("ID: ");
            int userId = Integer.parseInt(input.nextLine());

            UserModel user = users.searchUserById(userId);
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

    public static void searchUserByName() throws SQLException {

        try {

            System.out.print("Nome do Usuário: ");
            String user = input.nextLine();

            for (UserModel users : users.searchUsersByName(user)) {
                linha();
                System.out.println("ID: " + users.getIdUser());
                System.out.println("Nome: " + users.getUserName());
                System.out.println("Email: " + users.getEmail());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {

        EstadoMenu estadoMenu = EstadoMenu.USERS;

        while (estadoMenu != EstadoMenu.SAIR) {

            try {

                estadoMenu = switch (estadoMenu) {

                    case USERS -> menuUsers();
                    case BOOKS -> menuBooks();
                    case LOANS -> menuLoans();
                    default -> EstadoMenu.SAIR;

                };

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


    }

}