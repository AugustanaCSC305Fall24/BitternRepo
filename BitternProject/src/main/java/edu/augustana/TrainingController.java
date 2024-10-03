package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;

public class TrainingController {

    @FXML
    private void switchToWelcome() throws IOException {
        RadioApp.setRoot("WelcomeScreen");
    }
}