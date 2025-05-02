package com.example.chipion1;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;


import java.io.IOException;

public class MainController {
    @FXML private GridPane gameGrid;
    @FXML private Label currentPlayerLabel;
    @FXML private Label scoreLabel;
    @FXML private MenuBar menuBar;

    @FXML private TableView<PlayerScore> leaderboardTable;
    @FXML private TableColumn<PlayerScore, String> nameColumn;
    @FXML private TableColumn<PlayerScore, String> symbolColumn;
    @FXML private TableColumn<PlayerScore, String> scoreColumn;

    private GameModel gameModel;
    private Button[][] buttons;
    private ObservableList<PlayerScore> leaderboard;

    @FXML
    public void initialize() {
        gameModel = new GameModel();
        buttons = new Button[3][3];
        setupGameGrid();
        setupLeaderboardTable();
        updateUI();
        FadeTransition ft = new FadeTransition(Duration.millis(400), gameGrid);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

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

    private void setupLeaderboardTable() {
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().name));
        symbolColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().symbol));
        scoreColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().score)));

        leaderboard = FXCollections.observableArrayList(
                new PlayerScore("Joueur 1", "X", 0),
                new PlayerScore("Joueur 2", "O", 0)
        );

        leaderboardTable.setItems(leaderboard);
    }

    private void handleButtonClick(int row, int col) {
        char symbol = gameModel.getCurrentPlayer(); // capture avant move
        if (gameModel.makeMove(row, col)) {
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
        leaderboard.get(0).setScore(scores[0]);
        leaderboard.get(1).setScore(scores[1]);
        leaderboardTable.refresh();

        scoreLabel.setText("Score - " + gameModel.getPlayer1Name() + ": " + scores[0] +
                " | " + gameModel.getPlayer2Name() + ": " + scores[1]);
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
                startGameWithPlayers(controller.getPlayer1Name(), controller.getPlayer2Name());
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
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setDisable(false);
                buttons[i][j].setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-background-color: white; -fx-border-color: #2c3e50; -fx-border-width: 2;");
            }
        updateUI();
    }

    public void startGameWithPlayers(String p1, String p2) {
        gameModel.setPlayerNames(p1, p2);
        leaderboard.get(0).name = p1;
        leaderboard.get(1).name = p2;
        leaderboard.get(0).setScore(gameModel.getScores()[0]);
        leaderboard.get(1).setScore(gameModel.getScores()[1]);
        leaderboardTable.refresh();
        resetGame();
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public static class PlayerScore {
        String name;
        String symbol;
        int score;

        public PlayerScore(String name, String symbol, int score) {
            this.name = name;
            this.symbol = symbol;
            this.score = score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
