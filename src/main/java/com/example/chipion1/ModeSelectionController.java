package com.example.chipion1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class ModeSelectionController {
    @FXML
    private void handleClassicMode() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/chipion1/player_names.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Noms des joueurs");
        } catch (IOException e) {
            showError("Erreur de chargement de la fenêtre des noms.");
        }
    }

    @FXML
    private void handleTimeMode() {
        // À implémenter plus tard
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
