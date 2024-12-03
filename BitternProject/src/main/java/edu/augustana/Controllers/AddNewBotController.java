package edu.augustana.Controllers;
import edu.augustana.Chat.ChatRoom;
import edu.augustana.Radio.RadioApp;
import edu.augustana.bots.ChatBot;
import edu.augustana.bots.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;

import java.io.IOException;


public class AddNewBotController {



    @FXML
    private ComboBox<String> personalityTypeComboBox;

    @FXML
    private ColorPicker botColorPicker;

    @FXML
    private void initialize() {
        personalityTypeComboBox.getItems().addAll("FireDepartment", "NationalGuard", "RedCross", "Victim");
        personalityTypeComboBox.setValue(personalityTypeComboBox.getItems().get(0));
    }


    @FXML
    private void finishAddingBotAction(ActionEvent event) throws IOException {
        String personalityType = personalityTypeComboBox.getValue();
        ChatBot newBot;
        switch (personalityType) {
            case "FireDepartment":
                newBot = new FireDepartmentChatBot("FireDepartment", botColorPicker.getValue());
                break;
            case "NationalGuard":
                newBot = new NationalGuardChatBot("NationalGuard", botColorPicker.getValue());
                break;
            case "RedCross":
                newBot = new RedCrossDepartmentChatBot("RedCross", botColorPicker.getValue());
                break;
            case "Victim":
                newBot = new VictimChatBot("DisasterVictim", botColorPicker.getValue());
                break;
            default:
                throw new IllegalStateException("Invalid personality type: " + personalityType);

        }
        ChatRoom.getBots().add(newBot);
        RadioApp.setRoot("ScenarioScreen");
    }

    @FXML
    private void cancelAddingBotAction(ActionEvent event) throws IOException {
        RadioApp.setRoot("ScenarioScreen");
    }

}
