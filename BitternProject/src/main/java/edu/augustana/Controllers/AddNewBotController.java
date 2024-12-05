package edu.augustana.Controllers;
import edu.augustana.Chat.ChatClient;
import edu.augustana.Chat.ChatMessage;
import edu.augustana.Chat.ChatRoom;
import edu.augustana.Radio.RadioApp;
import edu.augustana.bots.ChatBot;
import edu.augustana.bots.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.io.IOException;


public class AddNewBotController {


    @FXML
    private TextField nameText;

    @FXML
    private TextField personalityText;

    @FXML
    private ComboBox<String> personalityTypeComboBox;

    @FXML
    private ColorPicker botColorPicker;
    @FXML
    private Slider botFrequencySlider;

    @FXML private Slider frequencySlider;

    @FXML
    private void initialize() {


    }


    @FXML
    private void finishAddingBotAction(ActionEvent event) throws IOException {
        String personalityType = personalityText.getText();
        String name = nameText.getText();
        ChatBot newBot = new ChatBot(name + "the" + personalityType, personalityType, botColorPicker.getValue(), botFrequencySlider.getValue());
        newBot.getChatLog().add(new ChatMessage(personalityType, "user", botColorPicker.getValue()));
        ChatRoom.getBots().add(newBot);
        RadioApp.setRoot("ScenarioScreen");

    }

    @FXML
    private void cancelAddingBotAction(ActionEvent event) throws IOException {
        RadioApp.setRoot("ScenarioScreen");
    }



}
