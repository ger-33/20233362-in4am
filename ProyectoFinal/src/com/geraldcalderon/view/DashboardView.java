package com.geraldcalderon.view;

import com.geraldcalderon.model.Usuario;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardView {

    public DashboardView(Usuario usuario) {
        Stage stage = new Stage();
        VBox root = new VBox(10);
        Label lblBienvenida = new Label("¡Bienvenido al Dashboard, " + usuario.getUsername() + "!");
        
        root.getChildren().add(lblBienvenida);
        Scene scene = new Scene(root, 400, 300);
        
        stage.setTitle("Dashboard Principal");
        stage.setScene(scene);
        stage.show();
    }

    void start(Stage stage) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}