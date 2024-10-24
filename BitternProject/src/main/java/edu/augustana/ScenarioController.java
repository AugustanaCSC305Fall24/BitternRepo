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

    private long lastClickTime = 0;
    private String input = "";
    private String translation;
    private UserInput userInput = new UserInput();
    private Boolean messagePlaying = false;

    //    @FXML
//    private void setFrequencyLabel() {
//        frequencyLabel.setText("Frequency: " + (int) frequencySlider.getValue() + " Hz");
//    }

    @FXML
    private void switchToWelcome(ActionEvent event) throws IOException {
        RadioApp.setRoot("WelcomeScreen");
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
    private void sendAction() throws LineUnavailableException{
        String msgText = userMessageTextField.getText();

        if (!msgText.isBlank()) {
            sendMessage(msgText, "User", Color.BLACK);
            checkBoxHandler(msgText);
            replyMessage(msgText);
        }
        userMessageTextField.clear();
        input = "";

    }

    private void playMessageSound(String message) throws LineUnavailableException, InterruptedException {
        while (messagePlaying) Thread.sleep(100);

        double delay = 1000 * (1200 - 37.2 * wpmSlider.getValue()) / (20 * wpmSlider.getValue());
        System.out.println(delay);

        messagePlaying = true;
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c == '.') {
                ToneGenerator.playDit(44100);
            } else if (c == '-') {
                ToneGenerator.playDah(44100);
            } else if (c == ' ') {
                Thread.sleep((long) (((double) 3 /19) * delay));
            } else if (c == '|') {
                Thread.sleep((long) (((double) 7 /19) * delay));
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Thread.sleep(2000);
        messagePlaying = false;
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
    private void dit() throws LineUnavailableException {
        input = userInput.userCWInput("dit");
        userMessageTextField.setText(input);
    }

    @FXML
    private void dah() throws LineUnavailableException {
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
                    playMessageSound(translation);
                } catch (LineUnavailableException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    public void botMessage(String message) {
        sendMessage(message, "Bot", Color.BLUE);
        checkBoxHandler(message);
    }

    public void sendMessage(String message, String sender, Color color) {
        ChatMessage newMessage = new ChatMessage(message, sender, color);
        ChatMessage.addMessage(newMessage);
        addMessageToChatLogUI(newMessage);
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

}
