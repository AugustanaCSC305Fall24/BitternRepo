package edu.augustana;

import javafx.scene.control.Label;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class TrainingController {

    @FXML private CheckBox phrasesCheckbox;
    @FXML private Button welcomeButton;
    @FXML private CheckBox randomizeCheckbox;
    @FXML private Button nextButton;
    @FXML private Button prevButton;
    @FXML private Label letterLabel;
    @FXML private Label morseCodeLabel;

    private final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private final String[] morseCodes = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
    private int index = 0;

    private final String[] phrases = {"CQ", "73", "SOS", "QTH", "QRM"};
    private final String[] phraseMorseCodes = {"-.-. --.-", "--... ...--", "... --- ...", "--.- - ....", "--.- .-. --"};

    @FXML
    void initialize() {
        updateLabel();
    }

    @FXML
    private void switchToWelcome() throws IOException {
        RadioApp.setRoot("WelcomeScreen");
    }

    @FXML
    private void handleNextButtonAction(ActionEvent event) {
        if (randomizeCheckbox.isSelected()) {
            randomizeLetters(event);
        } else {
            if (index < letters.length - 1) {
                index++;
            } else {
                index = 0; // Loop back to the start
            }
            updateLabel();
        }
    }

    @FXML
    private void handlePrevButtonAction(ActionEvent event) {
        if (randomizeCheckbox.isSelected()) {
            randomizeLetters(event);
        } else {
            if (index > 0) {
                index--;
            } else {
                index = letters.length - 1; // Loop back to the end
            }
            updateLabel();
        }
    }

    @FXML
    private void updateLabel() {
        if (phrasesCheckbox.isSelected()) {
            letterLabel.setText(phrases[index]);
            morseCodeLabel.setText(phraseMorseCodes[index]);
        } else {
            letterLabel.setText(letters[index]);
            morseCodeLabel.setText(morseCodes[index]);
        }
    }

    @FXML
    private void handlePhrasesCheckboxAction(ActionEvent event) {
        index = 0; // Reset index when switching modes
        updateLabel();
    }

    @FXML
    private void handleRandomizeCheckboxAction(ActionEvent event) {
        if (phrasesCheckbox.isSelected()) {
            nextButton.setDisable(true);
            prevButton.setDisable(true);
        } else {
            nextButton.setDisable(false);
            prevButton.setDisable(false);
        }
    }

    @FXML
    private void randomizeLetters(ActionEvent event) {
        int randomIndex = (int) (Math.random() * letters.length);
        index = randomIndex;
        updateLabel();
    }
}
