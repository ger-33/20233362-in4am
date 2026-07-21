package com.geraldcalderon; // O el paquete donde tengas tu clase Main

import com.geraldcalderon.view.loginView; // Importamos la vista del login
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Instanciamos y abrimos la pantalla de Login
        new loginView(); 
    }

    public static void main(String[] args) {
        // launch() arranca la infraestructura de JavaFX y ejecuta el método start()
        launch(args);
    }
}