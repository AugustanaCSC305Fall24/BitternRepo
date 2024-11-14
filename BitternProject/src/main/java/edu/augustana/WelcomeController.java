package edu.augustana;

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

    @FXML private void switchToScenario() throws IOException {
        switchSetUp("ScenarioScreen");
    }

    @FXML private void switchToTraining() throws IOException {
        switchSetUp("TrainingScreen");
    }


    @FXML private void switchToServer() throws IOException {
        switchSetUp("ServerScreen");
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
                        controller.ditOrDah(UserInput.Sounds.DIT);
                        break;
                    case M:
                    case S: // Bind 'S' key to dah
                        controller.ditOrDah(UserInput.Sounds.DAH);
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
