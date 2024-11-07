package edu.augustana;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerController extends Controller implements Initializable {

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
    @FXML private Slider frequencySlider;
    @FXML private Slider staticSlider;

    private String input = "";
    private UserInput userInput = new UserInput();
    WhiteNoise whiteNoise = new WhiteNoise();

    public void initialize(URL arg0, ResourceBundle arg1){
//        new Thread(() -> {
//            try { Thread.sleep(20); } catch (InterruptedException ex) { }
//            Platform.runLater(() -> whiteNoise.play());
//        }).start();
        new Thread(whiteNoise::play).start();

    }


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

    @Override
    public void dit() throws LineUnavailableException {
        userInput.clearInput(sendMessageTextbox.getText().isEmpty());
        input = userInput.userCWInput("dit");
        sendMessageTextbox.setText(input);
    }

    @Override
    public void dah() throws LineUnavailableException {
        userInput.clearInput(sendMessageTextbox.getText().isEmpty());
        input = userInput.userCWInput("dah");
        sendMessageTextbox.setText(input);
    }

    @Override
    public void sendAction() throws LineUnavailableException {
        // Here for send to server
        String msgText = sendMessageTextbox.getText();

        translateMessage(msgText);

        sendMessageTextbox.clear();
        input = "";

    }

    public void translateMessage(String msgText){
        sendTranslationTextbox.clear();
        sendTranslationTextbox.setText(Translator.morseToText(msgText));
    }

    public void setFrequency() {
        ToneGenerator.setFrequency((int) frequencySlider.getValue());
    }

    public void setWhiteNoiseVolume(){
        WhiteNoise.setVolume((int) staticSlider.getValue());
    }


}
