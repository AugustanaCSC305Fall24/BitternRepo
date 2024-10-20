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

    // Method to switch to the Welcome screen
    @FXML
    void switchToWelcome(ActionEvent event) throws IOException {
        RadioApp.setRoot("WelcomeScreen");
    }

}
