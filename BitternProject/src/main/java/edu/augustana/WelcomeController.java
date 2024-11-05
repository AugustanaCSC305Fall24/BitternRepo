package edu.augustana;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javax.sound.sampled.LineUnavailableException;

public class WelcomeController {

    private RadioApp app = new RadioApp();
    private UserInput userInput = new UserInput();

    public void setApp(RadioApp app) {
        this.app = app;
    }

//    @FXML
//    private void switchToScenario() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenarioScreen.fxml"));
//        Parent root = loader.load();
//        app.getScene().setRoot(root);
//
//        ScenarioController scenarioController = loader.getController();
//        scenarioController.setApp(app); // Set the app field
//
//        app.getScene().setOnKeyPressed(event -> {
//            try {
//                switch (event.getCode()) {
//                    case N:
//                    case A: // Bind 'A' key to dit
//                        scenarioController.dit();
//                        break;
//                    case M:
//                    case S: // Bind 'S' key to dah
//                        scenarioController.dah();
//                        break;
//                    case ENTER:
//                        scenarioController.sendAction();
//                        break;
//                    default:
//                        break;
//                }
//            } catch (LineUnavailableException e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }

//    @FXML private void switchToScenario() throws IOException {
//        switchSetUp("ScenarioScreen");
//    }

    @FXML private void switchToTraining() throws IOException {
//        RadioApp.setRoot("TrainingScreen");

        switchSetUp("TrainingScreen");
    }
    @FXML private void switchToServer() throws IOException {
        switchSetUp("ServerScreen");
    }
    @FXML private void switchToHowTo() throws IOException {
        RadioApp.setRoot("HowToScreen");
    }
    @FXML
    public void switchToScenarioMenu(ActionEvent actionEvent) throws IOException {
        RadioApp.setRoot("ScenarioMenu");

    }
    private void switchSetUp(String fxmlName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName + ".fxml"));
        Parent root = loader.load();
        app.getScene().setRoot(root);

        Controller controller = loader.getController();
        controller.setApp(app); // Set the app field

        app.getScene().setOnKeyPressed(event -> {
            try {
                switch (event.getCode()) {
                    case N:
                    case A: // Bind 'A' key to dit
                        controller.dit();
                        break;
                    case M:
                    case S: // Bind 'S' key to dah
                        controller.dah();
                        break;
                    case ENTER:
                        controller.sendAction();
                        break;
                    default:
                        break;
                }
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
