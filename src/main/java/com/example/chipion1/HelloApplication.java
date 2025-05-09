package com.example.chipion1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) throws Exception {
        // Chargement de la musique
        String musicFile = getClass().getResource("/com/example/chipion1/audio/fatrat_unity.mp3").toString();
        Media media = new Media(musicFile);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/chipion1/logo.png")));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/chipion1/mode_selection.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Choix du mode de jeu");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
