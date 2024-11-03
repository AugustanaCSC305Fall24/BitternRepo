package edu.augustana;

import javafx.scene.control.Label;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javax.sound.sampled.LineUnavailableException;


public class TrainingController extends Controller{

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
        } else if (phrasesCheckbox.isSelected()) {
            if (index < Translator.codeWords.length - 1) {
                index++;
            } else {
                index = 0; // Loop back to the start
            }
            updateLabel();
        }else {
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
        // Update label texts based on checkbox selection
        if (phrasesCheckbox.isSelected()) {
            letterLabel.setText(Translator.codeWords[index]);
            morseCodeLabel.setText(Translator.codeWordsMorse[index]);
        } else {
            letterLabel.setText(String.valueOf(Translator.englishLetters[index]));
            morseCodeLabel.setText(Translator.morseCodeLetters[index]);
        }

        // Create a new thread for playing the Morse sound
        new Thread(() -> {
            // Add delay before playing Morse sound
            try {
                Thread.sleep(500); // 500 milliseconds delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Play Morse sound
            String morseCode = morseCodeLabel.getText();
            for (char c : morseCode.toCharArray()) {
                try {
                    if (c == '.') {
                        ToneGenerator.playDit();
                    } else if (c == '-') {
                        ToneGenerator.playDah();
                    }
                    // Add a short pause between sounds
                    Thread.sleep(100);
                } catch (LineUnavailableException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start(); // Start the new thread
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
        updateLabel();
    }
}}