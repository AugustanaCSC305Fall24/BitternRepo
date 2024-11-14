package edu.augustana;

import javafx.scene.control.TextField;

import javax.sound.sampled.LineUnavailableException;

public abstract class Controller {

    RadioApp app = new RadioApp();
    TextField userText;
    UserInput userInput = new UserInput();
    double WPM = 20;
    String input = "";
    WhiteNoise whiteNoise = new WhiteNoise();


    public void setApp(RadioApp app) {this.app = app;}

    public void dit() throws LineUnavailableException {
        userInput.clearInput(userText.getText().isEmpty());
        input = userInput.userCWInput("dit", WPM);
        userText.setText(input);
    }

    public void dah() throws LineUnavailableException {
        userInput.clearInput(userText.getText().isEmpty());
        input = userInput.userCWInput("dah", WPM);
        userText.setText(input);
    }

    public void sendAction() throws LineUnavailableException {}


}
