package edu.augustana.Controllers;

import edu.augustana.Chat.ChatClient;
import edu.augustana.Chat.ChatMessage;
import edu.augustana.Chat.ChatRoom;
import edu.augustana.Input.Translator;
import edu.augustana.Radio.RadioApp;
import edu.augustana.Radio.ToneGenerator;
import edu.augustana.Radio.WhiteNoise;
import edu.augustana.bots.ChatBot;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class ScenarioController extends Controller implements Initializable {

    @FXML private Slider wpmSlider;
    @FXML private Button addBot;
    @FXML private ScrollPane chatLogScrollPane;
    @FXML private VBox chatLogVBox;
    @FXML private CheckBox translationCheckbox;
    @FXML public TextField userTextField = new TextField();
    @FXML private CheckBox englishCheckBox;
    @FXML private Slider toneFrequencySlider;
    @FXML private Slider frequencySlider;
    @FXML private Slider staticSlider;

    @FXML
    private ListView<ChatBot> botListView;

    private List<ChatBot> bots;


    private String translation;

    public void initialize(URL arg0, ResourceBundle arg1){
        userText = userTextField;
        WPM = wpmSlider.getValue();
        new Thread(whiteNoise::play).start();
        addMessageToChatLogUI(new ChatMessage("Hey! Help Me", "assistant", Color.BLACK));
        ChatRoom.setNewMessageEventListener(msg -> Platform.runLater(()->addMessageToChatLogUI(msg)));
        botListView.getItems().addAll(ChatRoom.getBots()); // add all pre-existing messages to the chat log ...check this

        botListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(ChatBot bot, boolean empty) {
                super.updateItem(bot, empty);
                if (empty || bot == null) {
                    setText(null);
                } else {
                    setText(bot.getName() + "   Frequency: " + bot.getFrequency() );
                }
            }
        });


        for (ChatMessage message : ChatRoom.getChatMessageList()) {
            addMessageToChatLogUI(message);
        }

        int BOT_SPEED_DELAY = 11 - 5; // speed 1 means 10 sec delay, speed 10 means 1 sec delay
        PauseTransition pause = new PauseTransition(Duration.seconds(BOT_SPEED_DELAY));
        pause.setOnFinished( e -> {
            // STOP running if the scene switched roots, and thus this chatLogVBox is longer visible
            if (chatLogVBox.getScene()!=null) {
                sendMessageFromRandomBot();
                pause.playFromStart(); // make it loop
            }
        });
        pause.play();
    }

    @FXML
    private void addBot (ActionEvent event) throws IOException{
        RadioApp.setRoot("AddNewBotView");
    }

    private void sendMessageFromRandomBot() {
        List<ChatMessage> messages = ChatRoom.getChatMessageList();
        if (messages.isEmpty()) {
            return; // No messages to respond to
        }

        ChatMessage lastMsg = messages.get(messages.size() - 1);
        String lastSender = lastMsg.getSender();
        List<ChatBot> bots = new ArrayList<>(ChatRoom.getBots());
        Collections.shuffle(bots);

        ChatBot matchingBot = null;

        for (ChatBot bot : bots) {
            if (!bot.getName().equals(lastSender)) {
                // Check if the frequency matches the bot's stored frequency
                if ((int) frequencySlider.getValue() == bot.getFrequency()) {
                    matchingBot = bot;
                    break;
                }
            }
        }

        if (matchingBot != null) {
            ChatMessage messageFromBot = matchingBot.generateResponseMessage(lastMsg);
            sendMessage(messageFromBot.getText(), messageFromBot.getSender(), messageFromBot.getColor());
        }
    }





    @FXML
    private void switchToWelcome(ActionEvent event) throws IOException {
        whiteNoise.exit();
        RadioApp.setRoot("WelcomeScreen");
    }

    @FXML
    void openHelpScreen(ActionEvent event) {
        RadioApp.createNewWindow("HelpPageScreen", "Help Page");
    }

    @FXML
    void openHowTo(ActionEvent event) {
        RadioApp.createNewWindow("HowToScreen", "How To Page");
    }

    @FXML
    private void clearChatLogAction() {
        List<ChatMessage> messages = ChatRoom.getChatMessageList();
        if (messages != null && !messages.isEmpty()) {
            messages.clear();
            chatLogVBox.getChildren().clear();
        }
    }

    @FXML @Override
    public void sendAction() throws LineUnavailableException {
        if (!userText.getText().isBlank()) {
            sendMessage(userText.getText(), "User", Color.BLACK);
        }
        super.sendAction();
    }

    public void addMessageToChatLogUI(ChatMessage messageToDisplay) {
        Label label = new Label(messageToDisplay.getSender() + ":  " + messageToDisplay.getText());
        label.setTextFill(messageToDisplay.getColor());
        label.setWrapText(true);
        label.setFont(Font.font("System", FontWeight.NORMAL, 11));
        chatLogVBox.getChildren().add(label);

        Platform.runLater(() -> chatLogScrollPane.setVvalue(1.0));
    }

    private void checkBoxHandler(String msgText) {
        if (translationCheckbox.isSelected()) {
            if (englishCheckBox.isSelected()) {
                translation = Translator.textToMorse(msgText);
                addTranslation(translation, "Translator", Color.RED);
            } else {
                translation = Translator.morseToText(msgText);
                addTranslation(translation, "Translator", Color.GREEN);
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
    public void addTranslation(String message, String sender, Color color){
        ChatMessage newMessage = new ChatMessage(message, sender, color);
        ChatRoom.addMessage(newMessage);
        addMessageToChatLogUI(newMessage);
    }

    private void sendMessage(String message, String sender, Color color) {
        ChatMessage newMessage = new ChatMessage(message, sender, color);
        ChatRoom.addMessage(newMessage);
        userText.clear();
        checkBoxHandler(message);

        if (englishCheckBox.isSelected()) {
            new Thread(() -> {
                ChatClient.sendMessage(newMessage.getText());
                ChatMessage lastMessage = ChatClient.getMessages().get(ChatClient.getMessages().size() - 1);

                new Thread(() -> {
                    if (translationCheckbox.isSelected()) {
                        try {
                            Thread.sleep(500);
                            String translation = Translator.textToMorse(lastMessage.getText());
                            ChatMessage.playMessageSound(translation, wpmSlider.getValue());
                        } catch (LineUnavailableException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();

                Platform.runLater(() -> {
                    ChatRoom.addMessage(lastMessage);
                });
            }).start();
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

    public void setWPM() {WPM = wpmSlider.getValue();}

    public void setFrequency() {
        ToneGenerator.setFrequency((int) toneFrequencySlider.getValue());
    }

    public void setWhiteNoiseVolume(){
        WhiteNoise.setVolume((int) staticSlider.getValue());
    }



}

