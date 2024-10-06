package edu.augustana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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

    }

    @FXML
    void playDotSound(ActionEvent event) {

    }

    @FXML
    void switchToWelcome(ActionEvent event) throws IOException {
        RadioApp.setRoot("WelcomeScreen");
    }

}
