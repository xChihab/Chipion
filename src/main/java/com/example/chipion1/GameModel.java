package com.example.chipion1;

public class GameModel {
    private static final int SIZE = 3;
    private final char[][] board;
    private char currentPlayer;
    private boolean gameOver;
    private final int[] scores;
    private String player1Name;
    private String player2Name;
    private int[][] winningPositions;

    public GameModel() {
        board = new char[SIZE][SIZE];
        scores = new int[2];
        resetGame();
    }

    public void resetGame() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                board[i][j] = ' ';
        currentPlayer = 'X';
        gameOver = false;
        winningPositions = null;
    }

    public boolean makeMove(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || board[row][col] != ' ' || gameOver)
            return false;

        board[row][col] = currentPlayer;

        if (checkWin()) {
            gameOver = true;
            scores[currentPlayer == 'X' ? 0 : 1]++;
        } else if (isBoardFull()) {
            gameOver = true;
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }

        return true;
    }

    private boolean isBoardFull() {
        for (char[] row : board)
            for (char cell : row)
                if (cell == ' ') return false;
        return true;
    }

    private boolean checkWin() {
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                winningPositions = new int[][]{{i, 0}, {i, 1}, {i, 2}};
                return true;
            }
        }

        for (int j = 0; j < SIZE; j++) {
            if (board[0][j] != ' ' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                winningPositions = new int[][]{{0, j}, {1, j}, {2, j}};
                return true;
            }
        }

        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            winningPositions = new int[][]{{0, 0}, {1, 1}, {2, 2}};
            return true;
        }

        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            winningPositions = new int[][]{{0, 2}, {1, 1}, {2, 0}};
            return true;
        }

        return false;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int[] getScores() {
        return scores;
    }

    public void setPlayerNames(String p1, String p2) {
        this.player1Name = p1;
        this.player2Name = p2;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public String getCurrentPlayerName() {
        return currentPlayer == 'X' ? player1Name : player2Name;
    }

    public String getWinnerName() {
        return gameOver && winningPositions != null ? (currentPlayer == 'X' ? player1Name : player2Name) : null;
    }

    public int[][] getWinningPositions() {
        return winningPositions;
    }
}
