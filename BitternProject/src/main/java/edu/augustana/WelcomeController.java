package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WelcomeController {

    @FXML private Button trainingButton;
    @FXML private Button scenarioButton;
    @FXML private Button serverButton;

    @FXML private void switchToTraining() throws IOException {
        RadioApp.setRoot("TrainingScreen");
    }
    @FXML private void switchToScenario() throws IOException {
        RadioApp.setRoot("ScenarioScreen");
    }
    @FXML private void switchToServer() throws IOException {
        RadioApp.setRoot("ServerScreen");
    }
    @FXML private void switchToHowTo() throws IOException {
        RadioApp.setRoot("HowToScreen");
    }
}
