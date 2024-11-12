package edu.augustana;

import javafx.scene.control.Label;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import javax.sound.sampled.LineUnavailableException;

import static edu.augustana.Translator.morseCodeLetters;


public class TrainingController extends Controller {

    @FXML private Button welcomeButton;
    @FXML private CheckBox randomizeCheckbox;
    @FXML private Button nextButton;
    @FXML private Button prevButton;
    @FXML private Label letterLabel;
    @FXML private TextField userTextBox;

    private String currentMorse;
    private int index = 0;
    private UserInput userInput = new UserInput();
    String input = "";

    private final double wpmTraining = 20;
    private final int englishSpaceIndex = 26;

    @FXML
    private void switchToWelcome() throws IOException {
        RadioApp.setRoot("WelcomeScreen");
    }

    @FXML
    void openHowTo(ActionEvent event) {
        RadioApp.createNewWindow("HowToScreen", "How To Page");
    }

    @FXML
    public void initialize() {
        updateLabel();
    }

    @FXML @Override
    public void dit() throws LineUnavailableException {
        input = userInput.userCWInput("dit", wpmTraining);
        ditDah();
    }

    @FXML @Override
    public void dah() throws LineUnavailableException {
        input = userInput.userCWInput("dah", wpmTraining);
        ditDah();
    }

    private void ditDah() {
        userTextBox.setText(input);
        if (userTextBox.getText().equalsIgnoreCase(currentMorse)) {
            resetTextBox();
            handleNextButtonAction(new ActionEvent());
        } else {
            userInput.clearInput(userTextBox.getText().isEmpty());
        }
    }

    @FXML
    private void handleNextButtonAction(ActionEvent event) {
        if (randomizeCheckbox.isSelected()) {
            randomizeLetters();
        } else if (index == englishSpaceIndex - 1) {
            index += 2;
        } else if (index < Translator.englishLetters.length - 1) {
            index++;
        } else {
            index = 0; // Loop back to the start
        }
        updateLabel();
    }

    @FXML
    private void handlePrevButtonAction(ActionEvent event) {
        if (randomizeCheckbox.isSelected()) {
            randomizeLetters();
        } else if (index == englishSpaceIndex + 1) {
            index -= 2;
        }else if (index > 0) {
            index--;
        } else {
            index = Translator.englishLetters.length - 1; // Loop back to the end
        }
        updateLabel();
    }

    @FXML
    private void updateLabel() {
        // Update label texts based on checkbox selection
        letterLabel.setText((String.valueOf(Translator.englishLetters[index])).toUpperCase());
        currentMorse = morseCodeLetters[index];

            // call method handle relpay button action instead of this code
        // Create a new thread for playing the Morse sound
        new Thread(() -> {
            // Add delay before playing Morse sound
            try {
                Thread.sleep(500); // 500 milliseconds delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Play Morse sound
            handlePlayButtonAction();
        }).start(); // Start the new thread
    }


    @FXML
    private void handleRandomizeCheckboxAction(ActionEvent event) {
            nextButton.setDisable(false);
            prevButton.setDisable(false);
            randomizeLetters();
    }

    @FXML
    private void randomizeLetters() {
        int randomIndex = (int) (Math.random() * Translator.englishLetters.length);
        index = randomIndex;
        resetTextBox();
        updateLabel();
    }

    @FXML
    private void handleReplayButtonAction(ActionEvent event) {
        handlePlayButtonAction();
        resetTextBox();
    }

    private void resetTextBox() {
        userInput.clearInput(true);
        userTextBox.setText("");
    }

    @FXML
    private void handlePlayButtonAction() {
        // Play Morse sound
        for (char c : currentMorse.toCharArray()) {
            try {
                if (c == '.') {
                    ToneGenerator.playDit();
                } else if (c == '-') {
                    ToneGenerator.playDah();
                }
                // Add a short pause between sounds
//                Thread.sleep(100);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }
}

