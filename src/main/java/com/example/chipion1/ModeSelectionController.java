package com.example.chipion1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ModeSelectionController {
    @FXML
    private void handleClassicMode() {
        try {
            // Chargement du FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/chipion1/player_names.fxml"));
            Parent root = loader.load();
            
            // Création d'une nouvelle scène
            Scene newScene = new Scene(root);
            newScene.getStylesheets().add(getClass().getResource("/com/example/chipion1/styles.css").toExternalForm());
            
            // Récupération de la fenêtre actuelle
            Stage stage = (Stage) ((Button) newScene.lookup("#classicModeButton")).getScene().getWindow();
            
            // Application de la nouvelle scène
            stage.setScene(newScene);
            stage.setTitle("Noms des joueurs");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur de chargement : " + e.getMessage());
        }
    }

    @FXML
    private void handleTimeMode() {
        showError("Mode contre la montre en cours de développement !");
    }

    @FXML
    private void handleRules() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Règles du jeu");
        alert.setHeaderText(null);
        alert.setContentText("Le Morpion est un jeu à deux joueurs sur une grille.\n\n" +
                "Chaque joueur place son symbole (X ou O) dans une case vide.\n\n" +
                "Le premier qui aligne ses symboles gagne.\n\n" +
                "Si toutes les cases sont remplies sans vainqueur, la partie est nulle.");
        alert.showAndWait();
    }

    @FXML
    private void handleQuit() {
        System.exit(0);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
