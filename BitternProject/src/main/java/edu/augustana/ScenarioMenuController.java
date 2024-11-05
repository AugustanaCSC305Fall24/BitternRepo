package edu.augustana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class ScenarioMenuController extends Controller {
    private RadioApp app = new RadioApp();
    @FXML
    private void switchToWelcome(ActionEvent event) throws IOException {
        app.switchToWelcome();
    }

    @FXML
    private void switchToScenario(ActionEvent event) throws IOException {
        RadioApp.setRoot("ScenarioScreen");
    }

}
