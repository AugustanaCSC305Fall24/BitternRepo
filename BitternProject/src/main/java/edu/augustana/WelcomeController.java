package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.sound.sampled.LineUnavailableException;

import static edu.augustana.ScenarioController.*;

public class WelcomeController {

    @FXML private void switchToTraining() throws IOException {
        RadioApp.setRoot("TrainingScreen");
    }
    @FXML private void switchToScenario() throws IOException, LineUnavailableException {
        Tone.line.open(Tone.af, Note.SAMPLE_RATE);
        Tone.line.start();
        RadioApp.setRoot("ScenarioScreen");
    }
    @FXML private void switchToServer() throws IOException {
        RadioApp.setRoot("ServerScreen");
    }
    @FXML private void switchToHowTo() throws IOException {
        RadioApp.setRoot("HowToScreen");
    }
}
