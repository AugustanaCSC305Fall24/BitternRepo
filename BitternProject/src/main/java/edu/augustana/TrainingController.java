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

    @FXML
    private Button nextButton;

    @FXML
    private Button prevButton;

    @FXML
    private Label letterLabel;

    @FXML
    private Label morseCodeLabel;

    @FXML
    private void switchToWelcome() throws IOException {
        RadioApp.setRoot("WelcomeScreen");
    }

    private final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private final String[] morseCodes = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
    private int index = 0;



    @FXML
    void initialize() {
        updateLabel();
    }

    @FXML
    private void handleNextButtonAction(ActionEvent event) {
        if (index < letters.length - 1) {
            index++;
        } else {
            index = 0; // Loop back to the start
        }
        updateLabel();
    }

    @FXML
    private void handlePrevButtonAction(ActionEvent event) {
        if (index > 0) {
            index--;
        } else {
            index = letters.length - 1; // Loop back to the end
        }
        updateLabel();
    }

    private void updateLabel() {
        letterLabel.setText(letters[index]);
        morseCodeLabel.setText(morseCodes[index]);
    }




}