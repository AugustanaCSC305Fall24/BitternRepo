package edu.augustana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundClass {

    // Helper method to play sound using ChatGPT
    static void playSound(String filePath) {
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
