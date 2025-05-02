package com.example.chipion1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML private GridPane gameGrid;
    @FXML private Label currentPlayerLabel;
    @FXML private Label scoreLabel;
    @FXML private MenuBar menuBar;

    private GameModel gameModel;
    private Button[][] buttons;

    @FXML
    public void initialize() {
        gameModel = new GameModel();
        buttons = new Button[3][3];
        setupGameGrid();
        updateUI();
    }

    private void setupGameGrid() {
        gameGrid.getChildren().clear();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = new Button();
                button.setPrefSize(100, 100);
                button.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-background-color: white; -fx-border-color: #2c3e50; -fx-border-width: 2;");
                final int r = row;
                final int c = col;
                button.setOnAction(e -> handleButtonClick(r, c));
                buttons[row][col] = button;
                gameGrid.add(button, col, row);
            }
        }
    }

    private void handleButtonClick(int row, int col) {
        if (gameModel.makeMove(row, col)) {
            char symbol = gameModel.getCurrentPlayer() == 'X' ? 'O' : 'X';
            buttons[row][col].setText(String.valueOf(symbol));
            buttons[row][col].setDisable(true);
            buttons[row][col].setStyle(
                    "-fx-font-size: 36px; -fx-font-weight: bold; -fx-background-color: white; -fx-border-color: #2c3e50; -fx-border-width: 2; -fx-text-fill: " +
                            (symbol == 'X' ? "#e74c3c" : "#3498db") + ";"
            );
            updateUI();

            if (gameModel.isGameOver()) {
                showGameOverDialog();
            }
        }
    }

    private void updateUI() {
        currentPlayerLabel.setText("Tour de : " + gameModel.getCurrentPlayerName());
        int[] scores = gameModel.getScores();
        scoreLabel.setText("Score - " + gameModel.getPlayer1Name() + ": " + scores[0] + " | " +
                gameModel.getPlayer2Name() + ": " + scores[1]);
    }

    @FXML
    private void handleNewGame() {
        showPlayerNamesDialog();
    }

    @FXML
    private void handleRules() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Règles du jeu");
        alert.setHeaderText(null);
        alert.setContentText("Le Morpion est un jeu à deux joueurs sur une grille 3x3.\n\n" +
                "Chaque joueur place son symbole (X ou O) dans une case vide.\n\n" +
                "Le premier qui aligne trois symboles gagne.\n\n" +
                "Si toutes les cases sont remplies sans vainqueur, la partie est nulle.");
        alert.showAndWait();
    }

    @FXML
    private void handleQuit() {
        System.exit(0);
    }

    private void showPlayerNamesDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/chipion1/player_names.fxml"));
            Parent root = loader.load();
            PlayerNamesController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Noms des joueurs");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (controller.isConfirmed()) {
                gameModel.setPlayerNames(controller.getPlayer1Name(), controller.getPlayer2Name());
                resetGame();
            }
        } catch (IOException e) {
            showError("Erreur de chargement de la fenêtre des noms.");
            e.printStackTrace();
        }
    }

    private void showGameOverDialog() {
        String message = gameModel.getWinnerName() != null
                ? gameModel.getWinnerName() + " a gagné !"
                : "Match nul !";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Fin de partie");
        alert.setHeaderText(message);
        alert.setContentText("Que souhaitez-vous faire ?");

        ButtonType newGame = new ButtonType("Nouvelle partie");
        ButtonType quit = new ButtonType("Quitter");
        alert.getButtonTypes().setAll(newGame, quit);

        alert.showAndWait().ifPresent(response -> {
            if (response == newGame) {
                showPlayerNamesDialog();
            } else {
                System.exit(0);
            }
        });
    }

    private void resetGame() {
        gameModel.resetGame();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setDisable(false);
                buttons[i][j].setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-background-color: white; -fx-border-color: #2c3e50; -fx-border-width: 2;");
            }
        }
        updateUI();
    }
    public GameModel getGameModel() {
        return gameModel;
    }
    public void startGameWithPlayers(String p1, String p2) {
        gameModel.setPlayerNames(p1, p2);
        resetGame();
    }


    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
