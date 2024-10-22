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

    @FXML private Slider volumeSlider;
    @FXML private ScrollPane chatLogScrollPane;
    @FXML private VBox chatLogVBox;
    @FXML private Button dahButton;
    @FXML private Button ditButton;
    @FXML private CheckBox translationCheckbox;
    @FXML public TextField userMessageTextField = new TextField();
    @FXML private Slider bandWidthSlider;
    @FXML private Slider frequencySlider;
    @FXML private CheckBox englishCheckBox;

    private long lastClickTime = 0;
    private String input = "";

    private UserInput userInput= new UserInput();

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
        if (messages == null || messages.isEmpty()) {
            return; // or handle the null/empty case appropriately
        }
        messages.clear();
        chatLogVBox.getChildren().clear();
    }

    @FXML
    private void sendAction() throws LineUnavailableException{
        String msgText = userMessageTextField.getText();
        String translation;
        if (!msgText.isBlank()) {
            ChatMessage newMessageFromUser = new ChatMessage(msgText, "User", Color.BLACK);
            ChatMessage.addMessage(newMessageFromUser);
            addMessageToChatLogUI(newMessageFromUser);

            if (translationCheckbox.isSelected()) {

                if (englishCheckBox.isSelected()) {
                    // Translate text to Morse code
                     translation = Translator.textToMorse(msgText);
                    if (translation.isEmpty()) {
                        translation = "Empty english translation";
                    }
                    ChatMessage newMessageFromTranslator = new ChatMessage(translation, "Translator", Color.RED);
                    addMessageToChatLogUI(newMessageFromTranslator);

                } else {
                    // Translate Morse code to text
                    translation = Translator.morseToText(msgText);
                    if (translation.isEmpty()) {
                        translation = "Invalid Morse Code";
                    }
                    ChatMessage newMessageFromTranslator = new ChatMessage(translation, "Translator", Color.GREEN);
                    addMessageToChatLogUI(newMessageFromTranslator);
                }

                String finalTranslation = translation;
                new Thread(() -> {
                    try {
                        playMessageSound(finalTranslation);
                    } catch (LineUnavailableException e) {
                        throw new RuntimeException(e);
                    }
                }).start();

                replyMessage(msgText);

                playMessageSound(translation);

            }
            // Clear the input field and reset the input string
            userMessageTextField.clear();
            input = "";
        }
    }

    private void playMessageSound(String message) throws LineUnavailableException {
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c == '.') {
                ToneGenerator.playDit(44100);
            } else if (c == '-') {
                ToneGenerator.playDah(44100);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
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
    private void dit() throws LineUnavailableException {
        userMessageTextField.setText(userInput.userDitInput());
    }

    @FXML
    private void dah() throws LineUnavailableException {
        userMessageTextField.setText(userInput.userDahInput());
    }

    private void replyMessage(String message) {
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
                        botMessage("Hello! How can I help you today?");
                        break;
                    case "How are you?":
                        botMessage("I'm doing well, thank you for asking!");
                        break;
                    case "Goodbye":
                        botMessage("Goodbye! Have a great day!");
                        break;
                    default:
                        botMessage("I'm sorry, I don't understand that message.");
                        break;
                }
            });
        }).start();
    }

    private void botMessage(String message) {


        ChatMessage newMessageFromBot = new ChatMessage(message, "Bot", Color.BLUE);
        ChatMessage.addMessage(newMessageFromBot);
        addMessageToChatLogUI(newMessageFromBot);

        if (translationCheckbox.isSelected()) {
            String translation;
            if (englishCheckBox.isSelected()) {
                // Translate text to Morse code
                translation = Translator.textToMorse(message);
                if (translation.isEmpty()) {
                    translation = "Empty english translation";
                }
                ChatMessage newMessageFromTranslator = new ChatMessage(translation, "Translator", Color.RED);
                addMessageToChatLogUI(newMessageFromTranslator);
            } else {
                // Translate Morse code to text
                translation = Translator.morseToText(message);
                if (translation.isEmpty()) {
                    translation = "Invalid Morse Code";
                }
                ChatMessage newMessageFromTranslator = new ChatMessage(translation, "Translator", Color.GREEN);
                addMessageToChatLogUI(newMessageFromTranslator);
            }
        }
    }


}
