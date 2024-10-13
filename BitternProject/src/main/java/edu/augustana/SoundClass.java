package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundClass {

    private static MediaPlayer mediaPlayer;

//    @FXML
//    public static void playDash() {
//        // Load the audio file and create a MediaPlayer
//        String audioPath = SoundClass.class.getResource("/Sound/dash.wav").toExternalForm();
//        Media sound = new Media(audioPath);
//        AudioClip audio = new AudioClip();
//        mediaPlayer = new MediaPlayer(sound);
//    }
//
//    @FXML
//    public static void playDot() {
//        // Load the audio file and create a MediaPlayer
//        String audioPath = SoundClass.class.getResource("/Sound/dot.wav").toExternalForm();
//        Media sound = new Media(audioPath);
//        mediaPlayer = new MediaPlayer(sound);
//    }

    // Helper method to play sound using ChatGPT
//    static void playSound(String filePath) {
//        File soundFile = new File(filePath);
//        if (soundFile.exists()) {
//            Media sound = new Media(soundFile.toURI().toString());
//            MediaPlayer mediaPlayer = new MediaPlayer(sound);
//            mediaPlayer.play();
//        } else {
//            System.out.println("Audio file not found: " + filePath);
//        }
//    }

    static void playSound(String filePath) {
        File soundFile = new File(filePath);
        if (soundFile.exists()) {
            AudioClip sound = new AudioClip(soundFile.toURI().toString());
            //MediaPlayer mediaPlayer = new MediaPlayer(sound);
            sound.play();
        } else {
            System.out.println("Audio file not found: " + filePath);
        }
    }

}
