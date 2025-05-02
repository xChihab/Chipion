package com.example.chipion1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Bonne méthode : chargement via le classpath
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/chipion1/logo.png")));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/chipion1/mode_selection.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Choix du mode de jeu");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
