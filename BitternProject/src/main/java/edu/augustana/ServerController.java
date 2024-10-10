package edu.augustana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;

public class ServerController {

    @FXML
    private Button dashButton;

    @FXML
    private Button dotButton;

    @FXML
    private Button welcomeButton;


    @FXML
    void playDashSound(ActionEvent event) {
        // Path to your dash sound file (make sure to provide the correct path)
        String dashSoundPath = "BitternProject\\src\\main\\resources\\Sound\\dash.wav";
        playSound(dashSoundPath);
    }


    @FXML
    void playDotSound(ActionEvent event) {
        // Path to your dot sound file (make sure to provide the correct path)
        String dotSoundPath = "BitternProject\\src\\main\\resources\\Sound\\dot.wav";
        playSound(dotSoundPath);
    }

    // Method to switch to the Welcome screen
    @FXML
    void switchToWelcome(ActionEvent event) throws IOException {
        RadioApp.setRoot("WelcomeScreen");
    }

    // Helper method to play sound using ChatGPT
    private void playSound(String filePath) {
        File soundFile = new File(filePath);
        if (soundFile.exists()) {
            Media sound = new Media(soundFile.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } else {
            System.out.println("Audio file not found: " + filePath);
        }
    }
}
