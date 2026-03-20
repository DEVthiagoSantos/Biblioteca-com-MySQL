package org.example.acessMenu;

import org.example.Enum.AdminMenu;
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

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

public class MenuAdmin {

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

    static String returnActualDate(LoanModel data) {

        if (data.getActual_return_date() == null) {
            return "Ainda não retornado.";
        } else {
            return data.getActual_return_date().format(formatter);
        }
    }

    public static void getLoan(LoanModel loan) {

        String lateFee = "";
        if (loan.getLateFee().compareTo(BigDecimal.ZERO) > 0) {
            lateFee = "Taxa: " + MenuUser.valueBalance(loan.getLateFee()) + "\n";
        }

        String data = returnActualDate(loan);
        String resultLoans = """
                    ID: %d
                    Usuário: %s
                    Livro: %s
                    Data do Empréstimo: %s
                    Data Limite de Retorno: %s
                    Data de Retorno: %s
                    %sStatus: %s""".formatted(loan.getIdLoan(),
                loan.getUser().getUserName(), loan.getBook().getTitle(),
                loan.getLoanDate().format(formatter),
                loan.getExpected_return_date().format(formatter),
                data, lateFee, loan.getStatus());

        linha();
        System.out.println(resultLoans);
    }

    public static AdminMenu menuUsers() {

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

    public static AdminMenu menuBooks() {

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

    public static AdminMenu menuLoans() {

        String menuLoans = """
                ============== LOANS ================
                [ 1 ] Cadastrar Empréstimo
                [ 2 ] Listar Empréstimos
                [ 3 ] Retornar Livro
                [ 4 ] Atualizar Empréstimo
                [ 5 ] Buscar Empréstimo (ID)
                [ 6 ] Buscar Empréstimos (Nome)
                [ 7 ] Consultas ->
                [ 8 ] <- Users
                [ 9 ] <- Books
                [ 10 ] Sair""";
        System.out.println(menuLoans);

        System.out.print("Escolha: ");
        int opcao = Integer.parseInt(input.nextLine());

        return executarLoan(opcao);
    }

    public static AdminMenu executarUsers(int opcao) {

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
                return AdminMenu.BOOKS;
            case 7 :
                return AdminMenu.LOANS;
            case 8 :
                return AdminMenu.SAIR;
        }

        return AdminMenu.USERS;
    }

    public static AdminMenu executarBooks(int opcao) {

        switch (opcao) {

            case 1 :
                insertBook();
                break;
            case 2 :
                getAllBooks();
                break;
            case 3 :
                updateBook();
                break;
            case 4 :
                getBookByID();
                break;
            case 5 :
                getBooksByTitle();
                break;
            case 6 :
                return AdminMenu.USERS;
            case 7 :
                return AdminMenu.LOANS;
            case 8 :
                return AdminMenu.SAIR;
        }

        return AdminMenu.BOOKS;
    }

    public static AdminMenu executarLoan(int opcao)  {

        switch (opcao) {

            case 1 :
                createLoan();
                break;
            case 2 :
                getAllLoans();
                break;
            case 3 :
                returnLoan();
                break;
            case 4 :
                updateLoan();
            case 5 :
                getLoanByID();
                break;
            case 6 :
                getLoanByUser();
                break;
            case 7 :
                // Consultas
            case 8 :
                return AdminMenu.USERS;
            case 9 :
                return AdminMenu.BOOKS;
            case 10 :
                return AdminMenu.SAIR;
        }

        return AdminMenu.LOANS;
    }

    // Metodos do Usuario
    public static void createUser() {

        try {

            System.out.print("Nome do Usuário:");
            String user = input.nextLine();
            System.out.print("Email: ");
            String email = input.nextLine();

            users.createUser(user, email);

            System.out.println("Usuário cadastrado com sucesso.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getAllUsers() {

        try {

            System.out.println("Listando Usuarios: ");
            for (UserModel user : users.getAllUsers()) {
                linha();
                System.out.println("ID: " + user.getIdUser());
                System.out.println("Nome: " + user.getUserName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Saldo: " + MenuUser.valueBalance(user.getAvailableBalance()));

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateUser() {

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

    public static void searchUserByID() {

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

    public static void searchUserByName() {

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

    // METODOS DOS LIVROS

    public static void insertBook() {

        try {
            System.out.print("Autor do Livro: ");
            String author = input.nextLine();
            System.out.print("Titulo do Livro: ");
            String title = input.nextLine();
            System.out.print("Quantos no total: ");
            int totalQuantity = Integer.parseInt(input.nextLine());
            System.out.println("Quantos disponíveis: ");
            int quantityAvailable = Integer.parseInt(input.nextLine());

            books.insertBook(author, title, totalQuantity, quantityAvailable);
            System.out.println("Livro inserido com sucesso.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getAllBooks() {

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

    public static void updateBook(){

        try {

            System.out.println("ID do livro que deseja atualizar ou alterar.");
            System.out.print("ID: ");
            int idBook = Integer.parseInt(input.nextLine());
            System.out.println("Ok.. Agora atualize ou altere o livro");

            System.out.print("Autor: ");
            String author = input.nextLine();
            System.out.print("Titulo: ");
            String title = input.nextLine();

            System.out.print("Quantos no total: ");
            int totalQuantity = Integer.parseInt(input.nextLine());
            System.out.print("Quantos disponíveis: ");
            int quantityAvailable = Integer.parseInt(input.nextLine());

            books.updateBook(author, title, totalQuantity, quantityAvailable, idBook);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getBookByID(){

        try {
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

    public static void getBooksByTitle() {

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
    public static void createLoan() {

        try {

            System.out.print("Usuário: ");
            String user = input.nextLine();
            System.out.print("Titulo do Livro: ");
            String titulo = input.nextLine();

            loans.createLoan(user, titulo);
            System.out.println("Empréstimo criado.");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public static void returnLoan() {

        try {

            System.out.print("Usuário: ");
            String usuario = input.nextLine();
            System.out.print("Livro: ");
            String livro = input.nextLine();

            loans.returnLoan(usuario, livro);
            System.out.println("Livro retornado.");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    public static void updateLoan() {

        try {

            System.out.print("Informe o ID do empréstimo: ");
            int idLoan = Integer.parseInt(input.nextLine());
            System.out.println("Ok.. agora atualize ou altere o Usuário ou o Livro");
            System.out.print("Usuário: ");
            String user = input.nextLine();
            System.out.print("Livro: ");
            String book = input.nextLine();

            loans.updateLoan(user, book, idLoan);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getAllLoans() {

        try {

            System.out.println("Listando Empréstimos: ");
            for (LoanModel loan : loans.getAllLoans()) {

                getLoan(loan);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getLoanByUser() {

        try {

            System.out.print("ID do usuário: ");
            String userName = input.nextLine();
            for (LoanModel loan : loans.getLoanByUser(userName)) {

                getLoan(loan);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getLoanByID() {

        try {

            System.out.println("ID do empréstimo: ");
            int idLoan = Integer.parseInt(input.nextLine());

            LoanModel loan = loans.getLoanById(idLoan);
            getLoan(loan);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void runSystem() {

        AdminMenu adminMenu = AdminMenu.USERS;

        while (adminMenu != AdminMenu.SAIR) {

            try {

                adminMenu = switch (adminMenu) {

                    case USERS -> menuUsers();
                    case BOOKS -> menuBooks();
                    case LOANS -> menuLoans();
                    default -> AdminMenu.SAIR;

                };

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
