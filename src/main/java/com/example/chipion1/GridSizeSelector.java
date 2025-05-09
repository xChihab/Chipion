package com.example.chipion1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GridSizeSelector {
    private int selectedSize = 3;
    private boolean confirmed = false;

    public static GridSize showDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(GridSizeSelector.class.getResource("/com/example/chipion1/grid_size.fxml"));
            Parent root = loader.load();
            GridSizeSelector controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Taille de la grille");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            return new GridSize(controller.selectedSize, controller.confirmed);
        } catch (IOException e) {
            e.printStackTrace();
            return new GridSize(3, false);
        }
    }

    @FXML
    private void handleStandardSize() {
        selectedSize = 3;
        confirmed = true;
        closeDialog();
    }

    @FXML
    private void handleLargeSize() {
        selectedSize = 5;
        confirmed = true;
        closeDialog();
    }

    @FXML
    private void handleCustomSize() {
        TextInputDialog dialog = new TextInputDialog("3");
        dialog.setTitle("Taille personnalisée");
        dialog.setHeaderText("Entrez la taille de la grille (3-10)");
        dialog.setContentText("Taille:");

        dialog.showAndWait().ifPresent(size -> {
            try {
                int customSize = Integer.parseInt(size);
                if (customSize >= 3 && customSize <= 10) {
                    selectedSize = customSize;
                    confirmed = true;
                    closeDialog();
                } else {
                    showError("La taille doit être comprise entre 3 et 10");
                }
            } catch (NumberFormatException e) {
                showError("Veuillez entrer un nombre valide");
            }
        });
    }

    private void closeDialog() {
        Stage stage = (Stage) ((Scene) root.getScene()).getWindow();
        stage.close();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

class GridSize {
    public final int size;
    public final boolean confirmed;

    public GridSize(int size, boolean confirmed) {
        this.size = size;
        this.confirmed = confirmed;
    }
} 