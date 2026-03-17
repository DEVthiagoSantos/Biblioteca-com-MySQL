package org.example;


import org.example.acessMenu.MenuAdmin;
import org.example.acessMenu.MenuUser;
import org.example.controller.UserController;
import org.example.model.UserModel;
import org.example.service.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    static UserService service = new UserService();
    static UserController users = new UserController(service);

    public static void main(String[] args) throws SQLException {

        Scanner input = new Scanner(System.in);

        while (true) {

                String menu = """
                    =============================
                    [ 1 ] Login User
                    [ 2 ] Login Admin
                    [ 3 ] sair""";

                System.out.println(menu);

                System.out.print("Escolha: ");
                int opcao = Integer.parseInt(input.nextLine());

                if (opcao == 1) {
                        System.out.print("ID do usuário: ");
                        int idUser = Integer.parseInt(input.nextLine());
                        try {

                            UserModel userModel = users.searchUserById(idUser);

                            if (userModel != null) {
                                MenuUser user = new MenuUser(idUser);
                                user.runSystem();
                            } else {
                                System.out.println("Usuário não existe.");
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                } else if (opcao == 2) {

                        System.out.print("Senha do Admin: ");
                        String senhaCorreta = "3131";
                        // Vou mudar a senha apenas para demonstração kkkk
                        String senha = input.nextLine();

                        if (senha.equals(senhaCorreta)) {
                            MenuAdmin menuAdmin = new MenuAdmin();
                            menuAdmin.runSystem();
                        } else {
                            System.out.println("Senha Incorreta.");
                        }

                } else if (opcao == 3) {
                        break;
                }

        }
    }

}