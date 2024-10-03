package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class TrainingController {

    @FXML private CheckBox phrasesCheckbox;
    @FXML private Button welcomeButton;

    @FXML
    private void switchToWelcome() throws IOException {
        RadioApp.setRoot("WelcomeScreen");
    }
}