package edu.augustana;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScenarioController extends Controller {

    @FXML private Slider wpmSlider;
    @FXML private ScrollPane chatLogScrollPane;
    @FXML private VBox chatLogVBox;
    @FXML private Button dahButton;
    @FXML private Button ditButton;
    @FXML private CheckBox translationCheckbox;
    @FXML public TextField userMessageTextField = new TextField();
    @FXML private CheckBox englishCheckBox;
    @FXML private Slider frequencySlider;

    private RadioApp app = new RadioApp();
    private String input = "";
    private String translation;
    private UserInput userInput = new UserInput();

    public void initialize() {
        addMessageToChatLogUI(new ChatMessage("Hi! Disaster Scenario Support Agent here, how can I assist you today?", "assistant", Color.BLACK));
    }

    @FXML
    private void switchToWelcome(ActionEvent event) throws IOException {
        app.switchToWelcome();
    }

    @FXML
    private void clearChatLogAction() {
        List<ChatMessage> messages = ChatMessage.getChatMessageList();
        if (messages != null && !messages.isEmpty()) {
            messages.clear();
            chatLogVBox.getChildren().clear();
        }
    }

    @FXML @Override
    public void sendAction() throws LineUnavailableException {
        String msgText = userMessageTextField.getText();

        if (!msgText.isBlank()) {
            sendMessage(msgText, "User", Color.BLACK);
            checkBoxHandler(msgText);
        }
        userMessageTextField.clear();
        input = "";
    }

    private void addMessageToChatLogUI(ChatMessage messageToDisplay) {
        Label label = new Label(messageToDisplay.getSender() + ":  " + messageToDisplay.getText());
        label.setTextFill(messageToDisplay.getColor());
        label.setWrapText(true);
        label.setFont(Font.font("System", FontWeight.NORMAL, 11));
        chatLogVBox.getChildren().add(label);

        Platform.runLater(() -> chatLogScrollPane.setVvalue(1.0));
    }

    @FXML @Override
    public void dit() throws LineUnavailableException {
        userInput.clearInput(userMessageTextField.getText().isEmpty());
        input = userInput.userCWInput("dit");
        userMessageTextField.setText(input);
    }

    @FXML @Override
    public void dah() throws LineUnavailableException {
        userInput.clearInput(userMessageTextField.getText().isEmpty());
        input = userInput.userCWInput("dah");
        userMessageTextField.setText(input);
    }

    private void checkBoxHandler(String msgText) {
        if (translationCheckbox.isSelected()) {
            if (englishCheckBox.isSelected()) {
                translation = Translator.textToMorse(msgText);
                sendMessage(translation, "Translator", Color.RED);
            } else {
                translation = Translator.morseToText(msgText);
                sendMessage(translation, "Translator", Color.GREEN);
            }
            new Thread(() -> {
                try {
                    ChatMessage.playMessageSound(translation, wpmSlider.getValue());
                } catch (LineUnavailableException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    public void sendMessage(String message, String sender, Color color) {
        ChatMessage newMessage = new ChatMessage(message, sender, color);
        ChatMessage.addMessage(newMessage);
        addMessageToChatLogUI(newMessage);
        userMessageTextField.clear();
        if (englishCheckBox.isSelected()) {


            ChatClient.sendMessage(newMessage.getText());
            ChatMessage lastMessage = ChatClient.getMessages().get(ChatClient.getMessages().size() - 1);
            ChatMessage.addMessage(lastMessage);
            addMessageToChatLogUI(lastMessage);
        }
    }

    public void botMessage(String message) {
        sendMessage(message, "Bot", Color.BLUE);
        checkBoxHandler(message);
    }

    public void replyMessage(String message) {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            Platform.runLater(() -> {
                switch (message) {
                    case "Hello":
                        botMessage("CW");
                        break;
                    case "Goodbye":
                        botMessage("73");
                        break;
                    default:
                        botMessage("AGN");
                        break;
                }
            });
        }).start();
    }

    public void setFrequency() {
        ToneGenerator.setFrequency((int) frequencySlider.getValue());
    }
}