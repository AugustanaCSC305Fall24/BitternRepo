package edu.augustana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class ServerController {

    @FXML private Button dashButton;
    @FXML private Button dotButton;
    @FXML private Button welcomeButton;
    @FXML private Button helpPageButton;

    @FXML
    void openHelpScreen(ActionEvent event) {
        RadioApp.createNewWindow("HelpPageScreen", "Help Page");
    }

    // Method to switch to the Welcome screen
    @FXML
    private void switchToWelcome(ActionEvent event) throws IOException {
        RadioApp.setRoot("WelcomeScreen");
    }

}
