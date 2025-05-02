package com.example.chipion1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.stage.Modality;

public class ModeSelectionController {

    @FXML
    private void startPvP() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/chipion1/player_names.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Noms des joueurs");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            PlayerNamesController controller = loader.getController();
            if (controller.isConfirmed()) {
                FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/com/example/chipion1/main.fxml"));
                Parent gameRoot = gameLoader.load();
                MainController gameController = gameLoader.getController();
                gameController.startGameWithPlayers(controller.getPlayer1Name(), controller.getPlayer2Name());

                Stage gameStage = new Stage();
                gameStage.setTitle("Morpion PvP");
                gameStage.setScene(new Scene(gameRoot));
                gameStage.show();

                // Fermer la fenêtre de sélection
                Stage currentStage = (Stage) root.getScene().getWindow();
                currentStage.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void startVsAI() {
        System.out.println("Fonctionnalité IA non encore implémentée.");
        // TODO: Ajouter implémentation IA
    }

    @FXML
    private void startOnline() {
        System.out.println("Fonctionnalité en ligne non encore disponible.");
        // TODO: Ajouter implémentation online
    }
}
