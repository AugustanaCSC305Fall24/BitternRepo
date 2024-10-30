package edu.augustana;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;

import javax.sound.sampled.LineUnavailableException;


public class RadioControls extends AnchorPane {

    @FXML private Button dahButton;
    @FXML private Button ditButton;
    @FXML private CheckBox englishCheckBox;
    @FXML private CheckBox translationCheckbox;
    @FXML private Slider wpmSlider;


    public RadioControls(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RadioControls.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

//    @FXML
//    public void dit() throws LineUnavailableException {
//        clearInput();
//        input = userInput.userCWInput("dit");
//        userMessageTextField.setText(input);
//    }
//
//    @FXML
//    public void dah() throws LineUnavailableException {
//        clearInput();
//        input = userInput.userCWInput("dah");
//        userMessageTextField.setText(input);
//    }

}
