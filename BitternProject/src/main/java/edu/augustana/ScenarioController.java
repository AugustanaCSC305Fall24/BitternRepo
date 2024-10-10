package edu.augustana;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.util.List;

public class ScenarioController {


    @FXML
    private ScrollPane chatLogScrollPane;

    @FXML
    private VBox chatLogVBox;

    @FXML
    private Button dahButton;

    @FXML
    private Button ditButton;

    @FXML
    private TextField userMessageTextField;

    @FXML
    private Label userNameLabel;

    @FXML
    private Button welcomeButton;

    private long lastClickTime = 0;

    private String input = "";


    @FXML
    private void switchToWelcome(ActionEvent event) throws IOException {
        RadioApp.setRoot("WelcomeScreen");
    }

    // got from exam 1 chatbots
    @FXML
    private void clearChatLogAction() {
        List<ChatMessage> messages = ChatRoom.getChatMessageList();  //ChatterBoxApp.getCurrentRoom().getChatMessageList();
        if (messages == null || messages.isEmpty()) {
            return; // or handle the null/empty case appropriately
        }

        ChatMessage firstSystemPost = messages.get(0);
        messages.clear();
        chatLogVBox.getChildren().clear();
        messages.add(firstSystemPost);
        addMessageToChatLogUI(firstSystemPost);
    }

    @FXML
    private void sendAction() {
        String msgText = userMessageTextField.getText();
        if (!msgText.isBlank()) {
            ChatMessage newMessageFromUser = new ChatMessage(msgText, "User", Color.BLACK);
            ChatRoom.addMessage(newMessageFromUser);
            addMessageToChatLogUI(newMessageFromUser);

            // Translate Morse code to text
            String translation = MorseCodeTranslator.morseToText(msgText);
            if(translation.isEmpty()){
                translation = "Invalid Morse Code";
            }
            ChatMessage newMessageFromTranslator = new ChatMessage(translation, "Translator", Color.GREEN);
            addMessageToChatLogUI(newMessageFromTranslator);

            // Clear the input field and reset the input string
            userMessageTextField.clear();
            input = "";
        }
    }

    private void addMessageToChatLogUI(ChatMessage messageToDisplay) {
        Label label = new Label(messageToDisplay.getSender() + ":  " + messageToDisplay.getText());
        label.setTextFill(messageToDisplay.getColor());
        label.setWrapText(true);
        label.setFont(Font.font("System", FontWeight.NORMAL, 11));
        chatLogVBox.getChildren().add(label);

        Platform.runLater(() -> chatLogScrollPane.setVvalue(1.0)); // scroll the scrollpane to the bottom
    }
    @FXML
    private void userDitInput(){
        String msgText = ".";
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < 2000) {
            input += ".";
        } else if(currentTime - lastClickTime < 3000){
            input += " ";
            input += ".";
        } else {
            input += " ";
            input += "|";
            input += " ";
        }

        userMessageTextField.setText(input);
        lastClickTime = currentTime;
    }

    @FXML
    private void userDahInput(){
        String msgText = "-";
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastClickTime < 2000) {
            input += "-";
        } else if(currentTime - lastClickTime < 3000){

            input += " ";
            input += "-";
        } else {
            input += " ";
            input += "|";
            input += " ";
        }

        lastClickTime = currentTime;
        userMessageTextField.setText(input);


    }




}
