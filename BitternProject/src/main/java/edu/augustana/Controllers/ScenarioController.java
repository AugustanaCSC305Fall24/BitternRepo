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

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

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
    @FXML private Label hertzLabel;

    @FXML private ListView<ChatBot> botListView;




    private String translation;
    private ChatBot bot;

    public void initialize(URL arg0, ResourceBundle arg1){

        userText = userTextField;
        WPM = wpmSlider.getValue();
        new Thread(whiteNoise::play).start();
        //addMessageToChatLogUI(new ChatMessage("Hey! Help Me", "assistant", Color.BLACK));
        ChatRoom.setNewMessageEventListener(msg -> Platform.runLater(()->addMessageToChatLogUI(msg)));
        botListView.getItems().addAll(ChatRoom.getBots()); // add all pre-existing messages to the chat log ...check this


        // Add a listener to the slider to update the hertz label text
       frequencySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int hertz = newValue.intValue(); // Get the slider's value as an integer
            hertzLabel.setText(hertz + " Hz"); // Update the label text
        });

//       // adds frequency to the bot list view
//        botListView.setCellFactory(lv -> new ListCell<>() {
//            @Override
//            protected void updateItem(ChatBot bot, boolean empty) {
//                super.updateItem(bot, empty);
//                if (empty || bot == null) {
//                    setText(null);
//                } else {
//                    setText(bot.getName() + "   Frequency: " + bot.getFrequency() );
//                }
//            }
//        });

//        for (ChatMessage message : ChatRoom.getChatMessageList()) {
//            addMessageToChatLogUI(message);
//        }


        // Add Key Event Handler for the Enter Key
        userTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    try {
                        sendAction();
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        });



        for (ChatMessage message : ChatRoom.getChatMessageList()) {
            addMessageToChatLogUI(message);
        }



//        chatLogVBox.getScene().getWindow().setOnHidden(event -> resetScenario());

//        int BOT_SPEED_DELAY = 11 - 5; // speed 1 means 10 sec delay, speed 10 means 1 sec delay
//        PauseTransition pause = new PauseTransition(Duration.seconds(BOT_SPEED_DELAY));
//        pause.setOnFinished( e -> {
//            // STOP running if the scene switched roots, and thus this chatLogVBox is longer visible
//            if (chatLogVBox.getScene()!=null) {
//                sendMessageFromRandomBot();
//                pause.playFromStart(); // make it loop
//            }
//        });
//        pause.play();

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

        // Get the last message sent
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
        whiteNoise.stopPlaying();
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

    private void addMessageToChatLogUI(ChatMessage messageToDisplay) {
        Label label = new Label(messageToDisplay.getSender() +":  " + messageToDisplay.getText());

        label.setTextFill(messageToDisplay.getColor());
        label.setWrapText(true);
        label.setFont(Font.font("System", FontWeight.NORMAL, 11));
        chatLogVBox.getChildren().add(label);

        Platform.runLater(() -> chatLogScrollPane.setVvalue(1.0));
    }

    private void checkBoxHandler(String msgText) {
        if (translationCheckbox.isSelected()) {
            String morse;

            // Check if the message matches any predefined phrase
            if (Translator.phraseToCodeWord.containsKey(msgText.toLowerCase())) {
                String codeWord = Translator.phraseToCodeWord.get(msgText.toLowerCase());
                morse = Translator.textToMorse(codeWord);
            } else {
                // Default to translating the whole message
                morse = Translator.textToMorse(msgText);
            }

            //addTranslation(morse, "Translator", Color.RED); check on this

            // Play Morse code
            new Thread(() -> {
                try {
                    ChatMessage.playMessageSound(morse, wpmSlider.getValue());
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
        addMessageToChatLogUI(newMessage);
        userText.clear();

        String translation;

        // Check if the message contains a phrase that maps to a code word
        if (Translator.phraseToCodeWord.containsKey(message.toLowerCase())) {
            String codeWord = Translator.phraseToCodeWord.get(message.toLowerCase());
            translation = Translator.textToMorse(codeWord);
        } else {
            translation = Translator.textToMorse(message);
        }



        checkBoxHandler(translation); //was message variable

        int currFreq = (int) frequencySlider.getValue();

        for (int i = 0; i < ChatRoom.getBots().size(); i++) {
            if (currFreq == ChatRoom.getBots().get(i).getFrequency()) {
                bot = ChatRoom.getBots().get(i);
            }
        }
        if (bot == null) {
            bot = ChatRoom.getBots().get(0); //default bot
        }
        System.out.println("Bot: " + bot.getName());



        if (englishCheckBox.isSelected()) {
            new Thread(() -> {
                ChatClient.sendMessage(newMessage.getText(), bot);
                ChatMessage lastMessage = ChatRoom.getChatMessageList().get(ChatRoom.getChatMessageList().size() - 1);

                new Thread(() -> {
                    if (translationCheckbox.isSelected()) {

                        try {
                            Thread.sleep(500);
                            String translationMessage = Translator.textToMorse(lastMessage.getText());
                            ChatMessage.playMessageSound(translationMessage, wpmSlider.getValue());
                        } catch (LineUnavailableException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();

                Platform.runLater(() -> {
                    //ChatRoom.addMessage(lastMessage);
                    addMessageToChatLogUI(lastMessage);
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

    @FXML
    public void setHertzLabel(){
        int hertz = (int) toneFrequencySlider.getValue();
        hertzLabel.setText(hertz + " Hz");
    }


}

