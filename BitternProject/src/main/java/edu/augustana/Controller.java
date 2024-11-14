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

    public void ditOrDah(UserInput.Sounds type) throws LineUnavailableException {
        userInput.clearInput(userText.getText().isEmpty());
        input = userInput.userCWInput(type, WPM);
        userText.setText(input);
    }

    public void sendAction() throws LineUnavailableException {
        userText.clear();
        input = "";
    }


}
