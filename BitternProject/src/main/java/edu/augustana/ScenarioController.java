package edu.augustana;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;
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


    @FXML
    private void switchToWelcome(ActionEvent event) throws IOException {
        RadioApp.setRoot("WelcomeScreen");
    }

    // got from exam 1 chatbots
    @FXML
    private void clearChatLogAction() {
        List<ChatMessage> messages = ChatRoom.getChatMessageList();    //ChatterBoxApp.getCurrentRoom().getChatMessageList();
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
            userMessageTextField.clear();
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
   private void playDashSound(ActionEvent event) {
        // Path to your dash sound file (make sure to provide the correct path)
        String dashSoundPath = "BitternProject\\src\\main\\resources\\Sound\\dash.wav";
        playSound(dashSoundPath);
    }


    @FXML
    private void playDotSound(ActionEvent event) {
        // Path to your dot sound file (make sure to provide the correct path)
        String dotSoundPath = "BitternProject\\src\\main\\resources\\Sound\\dot.wav";
        playSound(dotSoundPath);
    }

    private void playSound(String filePath) {
        File soundFile = new File(filePath);
        if (soundFile.exists()) {
            Media sound = new Media(soundFile.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } else {
            System.out.println("Audio file not found: " + filePath);
        }
    }


}
