package com.geraldcalderon.view;

import com.geraldcalderon.model.Usuario;
import com.geraldcalderon.model.UsuarioDAO;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class loginView extends Application {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Acceso al Sistema - Proyecto Final");

        // Componentes
        Label lblTitulo = new Label("Inicio de Sesión");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label lblUsuario = new Label("Usuario:");
        TextField txtUsuario = new TextField();

        Label lblPass = new Label("Contraseña:");
        PasswordField txtPass = new PasswordField();

        Button btnLogin = new Button("Ingresar");
        btnLogin.setStyle("-fx-background-color: #2b5b84; -fx-text-fill: white; -fx-font-weight: bold;");

        Label lblMensaje = new Label();

        // Layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(lblUsuario, 0, 0);
        grid.add(txtUsuario, 1, 0);
        grid.add(lblPass, 0, 1);
        grid.add(txtPass, 1, 1);

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(lblTitulo, grid, btnLogin, lblMensaje);

        // Acción
        btnLogin.setOnAction(e -> {
            String user = txtUsuario.getText().trim();
            String pass = txtPass.getText().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                lblMensaje.setText("Por favor complete todos los campos.");
                lblMensaje.setStyle("-fx-text-fill: red;");
                return;
            }

            Usuario usuarioLogueado = usuarioDAO.login(user, pass);

            if (usuarioLogueado != null) {
                lblMensaje.setText("¡Bienvenido " + usuarioLogueado.getNombreCompleto() + "!");
                lblMensaje.setStyle("-fx-text-fill: green;");

                // Abrir Dashboard
                DashboardView dashboard = new DashboardView(usuarioLogueado);
                try {
                    dashboard.start(new Stage());
                    primaryStage.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                lblMensaje.setText("Usuario o contraseña incorrectos.");
                lblMensaje.setStyle("-fx-text-fill: red;");
            }
        });

        Scene scene = new Scene(layout, 350, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}