package org.example;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.UserController;
import org.example.model.UserModel;
import org.example.service.UserService;

import java.math.BigDecimal;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        UserController userController = new UserController(new UserService());

        TableView<UserModel> table = new TableView<>();
        TableColumn<UserModel, Integer> idCol
                = new TableColumn<>("ID");
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("idUser"));

        TableColumn<UserModel, String> userCol
                = new TableColumn<>("Nome");
        userCol.setCellValueFactory(
                new PropertyValueFactory<>("userName")
        );

        TableColumn<UserModel, String> emailCol
                = new TableColumn<>("Email");
        emailCol.setCellValueFactory(
                new PropertyValueFactory<>("email")
        );

        TableColumn<UserModel, BigDecimal> balanceCol =
                new TableColumn<>("Saldo");
        balanceCol.setCellValueFactory(
                new PropertyValueFactory<>("availableBalance")
        );

        table.getColumns().addAll(idCol, userCol, emailCol, balanceCol);

        Button button = new Button("Listar Usuarios");

        button.setOnAction(e -> {
            try {
                table.getItems().setAll(
                        userController.getAllUsers()
                );
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox root = new VBox(button, table);

        Scene scene = new Scene(root, 400, 300);

        stage.setTitle("Sistema Biblioteca");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

        launch();
    }
}
