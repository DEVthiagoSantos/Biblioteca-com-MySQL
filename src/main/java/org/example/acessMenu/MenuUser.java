package org.example.acessMenu;

import org.example.Enum.UserMenu;
import org.example.controller.LoanController;
import org.example.controller.UserController;
import org.example.model.LoanModel;
import org.example.model.UserModel;
import org.example.service.LoanService;
import org.example.service.UserService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class MenuUser {

    private static int userId;

    public MenuUser(int userId) {
        MenuUser.userId = userId;
    }

    static UserService userService = new UserService();
    static LoanService loanService = new LoanService();
    static UserController users = new UserController(userService);
    static LoanController loans = new LoanController(loanService);


    public static Scanner input = new Scanner(System.in);

    static void linha() {
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }

    static String valueBalance(BigDecimal value) {

        BigDecimal form = value.setScale(2, RoundingMode.HALF_UP);
        DecimalFormat fr = new DecimalFormat("###,##0.00");

        return fr.format(form);
    }

    public static UserMenu menuUsers()  {

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
                [ 9 ] Depositar
                [ 10 ] Sair""";

        System.out.println(menuUser);

        System.out.print("Escolha: ");
        int opcao = Integer.parseInt(input.nextLine());

        return executarUsers(opcao);
    }

    public static UserMenu executarUsers(int opcao)  {

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
                MenuAdmin.getBookByID();
                break;
            case 5 :
                MenuAdmin.getBooksByTitle();
                break;
            case 6 :
                MenuAdmin.getAllBooks();
                break;
            case 7 :
                createLoan();
                break;
            case 8 :
                returnLoan();
                break;
            case 9 :
                depositeBalance();
                break;
            case 10 :
                return UserMenu.SAIR;
        }

        return UserMenu.USERS;
    }

    // Metodos do Usuario

    public static void updateUser() {

        try {
            System.out.print("Nome do Usuário: ");
            String nome = input.nextLine();
            System.out.print("Email: ");
            String email = input.nextLine();
            users.updateUser(nome, email, userId);

            System.out.println("Usuário atualizado");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void searchUserByID() {

        try {

            UserModel user = users.searchUserById(userId);
            String resultUser = """
                    ID: %d
                    Nome: %s
                    Email: %s
                    Saldo: R$ %s""".formatted(user.getIdUser(),
                    user.getUserName(), user.getEmail(),
                    valueBalance(user.getAvailableBalance()));
            linha();
            System.out.println(resultUser);

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    // Metodos de empréstimo
    public static void createLoan() {

        try {

            UserModel user = users.searchUserById(userId);
            System.out.print("Titulo do Livro: ");
            String titulo = input.nextLine();

            loans.createLoan(user.getUserName(), titulo);
            System.out.println("Empréstimo criado.");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public static void returnLoan() {

        try {

            UserModel user = users.searchUserById(userId);
            System.out.print("Livro: ");
            String livro = input.nextLine();

            loans.returnLoan(user.getUserName(), livro);
            System.out.println("Livro retornado.");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    public static void getLoanByUser(){

        try {

            for (LoanModel loan : loans.getLoanByUser(users.searchUserById(userId)
                    .getUserName())) {

                MenuAdmin.getLoan(loan);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void depositeBalance(){

        try {

            System.out.println("Inserir valor: ");
            double value = Double.parseDouble(input.nextLine());
            users.depositBalance(value, userId);
            System.out.println("Deposito feito com sucesso.");
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
