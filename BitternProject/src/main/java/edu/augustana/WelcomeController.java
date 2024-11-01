package edu.augustana;
import javafx.event.ActionEvent;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javax.sound.sampled.LineUnavailableException;

public class WelcomeController {

    private RadioApp app = new RadioApp();

    public void setApp(RadioApp app) {
        this.app = app;
    }

//    @FXML
//    private void switchToScenario(ActionEvent event) throws IOException {
//        app.switchToScenario();
//    }

    @FXML
    public void switchToScenario(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenarioScreen.fxml"));
        Parent root = loader.load();

        app.getScene().setRoot(root);

        ScenarioController scenarioController = loader.getController();
        scenarioController.setApp(app); // Set the app field

        app.getScene().setOnKeyPressed(event -> {
            try {
                switch (event.getCode()) {
                    case N:
                    case A: // Bind 'A' key to dit
                        scenarioController.dit();
                        break;
                    case M:
                    case S: // Bind 'S' key to dah
                        scenarioController.dah();
                        break;
                    case ENTER:
                        scenarioController.sendAction();
                        break;
                    default:
                        break;
                }
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        });

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
