package com.example.chipion1;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PlayerNamesController {
    @FXML
    private TextField player1NameField;
    @FXML
    private TextField player2NameField;

    private boolean confirmed = false;

    @FXML
    private void handleConfirm() {
        if (!player1NameField.getText().trim().isEmpty() &&
                !player2NameField.getText().trim().isEmpty()) {
            confirmed = true;
            closeWindow();
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) player1NameField.getScene().getWindow();
        stage.close();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getPlayer1Name() {
        return player1NameField.getText().trim();
    }

    public String getPlayer2Name() {
        return player2NameField.getText().trim();
    }
}
