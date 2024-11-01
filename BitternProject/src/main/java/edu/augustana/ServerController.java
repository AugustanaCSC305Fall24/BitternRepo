package edu.augustana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class ServerController {

    @FXML private Button welcomeButton;
    @FXML private Button helpPageButton;
    @FXML private Button notepadButton;
    @FXML private Button repeatSoundButton;
    @FXML private Label replyLabel;
    @FXML private TextField replyTextbox;
    @FXML private TextField replyTranslationTextbox;
    @FXML private Button sendMessage;
    @FXML private TextField sendMessageTextbox;
    @FXML private TextField sendTranslationTextbox;
    @FXML private CheckBox translationCheckbox;

    @FXML
    void openHelpScreen(ActionEvent event) {
        RadioApp.createNewWindow("HelpPageScreen", "Help Page");
    }

    @FXML
    void openNotepad(ActionEvent event){
        RadioApp.createNewWindow("NotepadScreen", "Notepad");
    }

    // Method to switch to the Welcome screen
    @FXML
    private void switchToWelcome(ActionEvent event) throws IOException {
        RadioApp.setRoot("WelcomeScreen");
    }

}
