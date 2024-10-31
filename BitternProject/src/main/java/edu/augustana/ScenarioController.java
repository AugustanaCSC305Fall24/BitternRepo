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
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

public class ScenarioController {

    @FXML private Slider wpmSlider;
    @FXML private ScrollPane chatLogScrollPane;
    @FXML private VBox chatLogVBox;
    @FXML private Button dahButton;
    @FXML private Button ditButton;
    @FXML private CheckBox translationCheckbox;
    @FXML public TextField userMessageTextField = new TextField();
    @FXML private CheckBox englishCheckBox;
    private RadioApp app;
    private String input = "";
    private String translation;
    private UserInput userInput = new UserInput();

    public void setApp(RadioApp app) {
        this.app = app;
    }

    @FXML
    private void switchToWelcome(ActionEvent event) throws IOException {
        app.switchToWelcome();
    }

    // got from exam 1 chatbots
    @FXML
    private void clearChatLogAction() {
        List<ChatMessage> messages = ChatMessage.getChatMessageList();
        if (messages != null && !messages.isEmpty()) {
            messages.clear();
            chatLogVBox.getChildren().clear();


        }
    }

    @FXML
    public void sendAction() throws LineUnavailableException{
        String msgText = userMessageTextField.getText();

        if (!msgText.isBlank()) {
            sendMessage(msgText, "User", Color.BLACK);
            checkBoxHandler(msgText);
            replyMessage(msgText);
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

        Platform.runLater(() -> chatLogScrollPane.setVvalue(1.0)); // scroll the scrollpane to the bottom
    }

//    private void keyPress() {
//        KeyEvent event =
//        event.getKeyChar();
//    }

//     Found base code on Stack Overflow
    @FXML
    private void keyPressed(KeyEvent event) throws LineUnavailableException {
        if (event.getKeyCode() == 'm') {
            dah();
        } else if (event.getKeyCode() == 'n') {
            dit();
        } else if (event.getKeyCode() == '\n') {
            sendAction();
        }
    }

    @FXML
    public void dit() throws LineUnavailableException {
        clearInput();
        input = userInput.userCWInput("dit");
        userMessageTextField.setText(input);
    }

    @FXML
    public void dah() throws LineUnavailableException {
        clearInput();
        input = userInput.userCWInput("dah");
        userMessageTextField.setText(input);
    }

    private void checkBoxHandler(String msgText) {
        if (translationCheckbox.isSelected()) {
            if (englishCheckBox.isSelected()) {
                // Translate text to Morse code
                translation = Translator.textToMorse(msgText);
                sendMessage(translation, "Translator", Color.RED);

            } else {
                // Translate Morse code to text
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
    }

    public void botMessage(String message) {
        sendMessage(message, "Bot", Color.BLUE);
        checkBoxHandler(message);
    }

    public void replyMessage(String message) {
        new Thread(() -> {
            try {
                // Introduce a delay of 2 seconds (2000 milliseconds)
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Handle thread interruption
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
    public void clearInput() {
        if (userMessageTextField.getText().isEmpty()){
            userInput.clearInput();
        }
    }

}
