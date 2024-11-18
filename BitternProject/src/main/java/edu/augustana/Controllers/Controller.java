package edu.augustana.Controllers;

import edu.augustana.Radio.RadioApp;
import edu.augustana.Input.UserInput;
import edu.augustana.Radio.WhiteNoise;
import javafx.scene.control.TextField;

import javax.sound.sampled.LineUnavailableException;

public abstract class Controller {

    RadioApp app = new RadioApp();
    TextField userText;
    UserInput userInput = new UserInput();
    double WPM = 15;
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
