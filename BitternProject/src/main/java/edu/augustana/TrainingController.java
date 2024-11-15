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
    @FXML private CheckBox cwCheckbox;

    private String currentMorse;
    private int index = 0;

    private final int englishSpaceIndex = 26;
    private int numTranslatableItems;

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
        userText = userTextBox;
        numTranslatableItems = Translator.englishLetters.length;
        updateLabel();
    }

    @FXML
    private void updateLabel() {
        // Check if CW phrase or English letter
        if (index < Translator.englishLetters.length) {
            letterLabel.setText((String.valueOf(Translator.englishLetters[index])).toUpperCase());
            currentMorse = morseCodeLetters[index];
        } else {
            int tempIndex = index - Translator.englishLetters.length;
            String currentCW = Translator.codeWords[tempIndex];
            letterLabel.setText(currentCW);
            currentMorse = Translator.textToMorse(currentCW);
        }

        // Create a new thread for playing the Morse sound
        new Thread(() -> {
            try {
                Thread.sleep(200); // 500 milliseconds delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Play Morse sound
            try {
                ChatMessage.playMessageSound(currentMorse, WPM);
            } catch (LineUnavailableException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start(); // Start the new thread
    }

    @Override
    public void ditOrDah(UserInput.Sounds type) throws LineUnavailableException {
        super.ditOrDah(type);
        if (userTextBox.getText().equalsIgnoreCase(currentMorse)) {
            resetTextBox();
            shiftIndex(true);
        }
    }

    private void shiftIndex(Boolean moveForward) {
        int numToAdd = 1;
        if (!moveForward) { // Check if called to move index backwards instead of forwards
            numToAdd = -1;
        }
        if (randomizeCheckbox.isSelected()) {
            randomizeLetters();
        } else if (index == englishSpaceIndex - numToAdd) {
            index += 2 * numToAdd; // Skip over the English "space"
        } else if (index == numTranslatableItems - 1 && moveForward) {
            index = 0; // Loop back to the start from end
        } else if (index == 0 && !moveForward) {
            index = numTranslatableItems -1; // Loop around to end from start
        } else {
            index += numToAdd;
        }
        updateLabel();
    }

    @FXML
    private void handleNextButtonAction(ActionEvent event) {
        shiftIndex(true);
    }

    @FXML
    private void handlePrevButtonAction(ActionEvent event) {
        shiftIndex(false);
    }

    @FXML
    private void randomizeLetters() {
        int randomIndex = (int) (Math.random() * numTranslatableItems);
        index = randomIndex;
        resetTextBox();
        updateLabel();
    }

    @FXML
    private void handleCWCheckboxAction() {
        if (cwCheckbox.isSelected()) {
            numTranslatableItems = Translator.englishLetters.length + Translator.codeWords.length;
        } else {
            numTranslatableItems = Translator.englishLetters.length;
        }
    }

    @FXML
    private void handleReplayButtonAction(ActionEvent event) throws LineUnavailableException, InterruptedException {
        resetTextBox();
        ChatMessage.playMessageSound(currentMorse, WPM);
    }

    private void resetTextBox() {
        userInput.clearInput(true);
        userTextBox.setText("");
    }
}

