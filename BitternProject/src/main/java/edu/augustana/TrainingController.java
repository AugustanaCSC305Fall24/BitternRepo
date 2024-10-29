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

    private int index = 0;

    @FXML
    private void switchToWelcome() throws IOException {
        RadioApp.setRoot("WelcomeScreen");
    }

    @FXML
    public void initialize() {
        updateLabel();
    }

    @FXML
    private void handleNextButtonAction(ActionEvent event) {
        if (randomizeCheckbox.isSelected()) {
            randomizeLetters();
        } else {
            if (index < Translator.englishLetters.length - 1) {
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
            randomizeLetters();
        } else {
            if (index > 0) {
                index--;
            } else {
                index = Translator.englishLetters.length - 1; // Loop back to the end
            }
            updateLabel();
        }
    }

    @FXML
    private void updateLabel() {
        if (phrasesCheckbox.isSelected()) {
            letterLabel.setText(Translator.codeWords[index]);
            morseCodeLabel.setText(Translator.codeWordTranslation[index]);
        } else {
            letterLabel.setText(String.valueOf(Translator.englishLetters[index]));
            morseCodeLabel.setText(Translator.morseCodeLetters[index]);
        }
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
    private void randomizeLetters() {
        int randomIndex = (int) (Math.random() * Translator.englishLetters.length);
        index = randomIndex;
        updateLabel();
    }

    @FXML
    void handlePhrasesCheckbox(ActionEvent event) {
        if (phrasesCheckbox.isSelected()) {
            index = 0;
            nextButton.setDisable(true);
            prevButton.setDisable(true);
        } else {
            nextButton.setDisable(false);
            prevButton.setDisable(false);
        }
        updateLabel();
    }
}