package edu.augustana;
import javafx.event.ActionEvent;

import java.io.IOException;
import javafx.fxml.FXML;

public class WelcomeController {

    private RadioApp app;

    public void setApp(RadioApp app) {
        this.app = app;
    }

    @FXML
    private void switchToScenario(ActionEvent event) throws IOException {
        app.switchToScenario();
    }

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
