package edu.augustana.Controllers;

import edu.augustana.Chat.ChatClient;
import edu.augustana.Chat.ChatMessage;
import edu.augustana.Chat.ChatRoom;
import edu.augustana.Input.Translator;
import edu.augustana.Radio.RadioApp;
import edu.augustana.Radio.ToneGenerator;
import edu.augustana.Radio.WhiteNoise;
import edu.augustana.Chat.ChatBot;
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
import java.io.*;
import java.net.URL;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.stage.FileChooser;

import java.util.List;

public class ScenarioController extends Controller implements Initializable {

    @FXML private Slider wpmSlider;
    @FXML private ScrollPane chatLogScrollPane;
    @FXML private VBox chatLogVBox;
    @FXML public TextField userTextField = new TextField();
    @FXML private Slider toneFrequencySlider;
    @FXML private Slider bandwidthSlider;
    @FXML private Slider  bandPassSlider;
    @FXML private Label bandwidthLabel;
    @FXML private Label bandPassLabel;
    @FXML private Label toneLabel;
    @FXML private ListView<ChatBot> botListView;

    private ChatBot bot;
    public static final File DEFAULT_USER_PREFERENCES_FILE = new File("user_preferences.json");

    public void initialize(URL arg0, ResourceBundle arg1){
        userText = userTextField;
        WPM = wpmSlider.getValue();
        new Thread(whiteNoise::play).start();
        botListView.getItems().addAll(ChatRoom.getBots());

        // Add a listener to the slider to update the hertz label text
        bandwidthSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int hertz = newValue.intValue();
            bandwidthLabel.setText(hertz + " Hz");
        });

        // Add a listener to the slider to update the hertz label text
        toneFrequencySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int hertz = newValue.intValue();
            toneLabel.setText(hertz + " Hz");
        });

        // Add a listener to the slider to update the hertz label text
        bandPassSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double minInput = 7.000;  // Minimum slider value
            double maxInput = 7.067;  // Maximum slider value
            int minOutput = -80;      // Minimum volume (mute) -80 dB
            int maxOutput = 6;        // Maximum volume (loud)

            double sliderValue = newValue.doubleValue(); // Current slider value
            int volumeLevel = (int) ((sliderValue - minInput) / (maxInput - minInput) * (maxOutput - minOutput) + minOutput);

            WhiteNoise.setVolume(volumeLevel); // Adjust white noise volume
            bandPassLabel.setText(String.format("%.3f MHz", sliderValue)); // Update label with MHz value
        });

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
    }

    @FXML
    private void addBot (ActionEvent event) throws IOException{
        RadioApp.setRoot("AddNewBotView");
    }

    @FXML
    private void switchToWelcome(ActionEvent event) throws IOException {
        whiteNoise.reset();
        whiteNoise.stopPlaying();
        saveScenarioToFile();
        clearScenarioData();
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

    public void clearScenarioData() {
        ChatRoom.getChatMessageList().clear();
        ChatRoom.getBots().clear();
        chatLogVBox.getChildren().clear();
        botListView.getItems().clear();
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
            if (ChatRoom.getBots().isEmpty()) {
                showNoBotAlert(); // Trigger the alert
                return; // Stop further processing
            }
            sendMessage(userText.getText(), "User", Color.BLACK);
        }
        super.sendAction();
    }

    private void showNoBotAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Bot Selected");
        alert.setHeaderText("No bot selected");
        alert.setContentText("Please add a bot to the chat room before sending a message.");
        alert.showAndWait();
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
            String morse;

            // Check if the message matches any predefined phrase
            if (Translator.phraseToCodeWord.containsKey(msgText.toLowerCase())) {
                String codeWord = Translator.phraseToCodeWord.get(msgText.toLowerCase());
                morse = Translator.textToMorse(codeWord);
            } else {
                // Default to translating the whole message
                morse = Translator.textToMorse(msgText);
            }

            // Play Morse code
            new Thread(() -> {
                try {
                    ChatMessage.playMessageSound(morse, wpmSlider.getValue());
                } catch (LineUnavailableException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
    }

    private void sendMessage(String message, String sender, Color color) {
        ChatMessage newMessage = new ChatMessage(message, sender, color);
        addMessageToChatLogUI(newMessage);
        userText.clear();

        String translation;
        if(ChatRoom.getBots().isEmpty()){ //fix later
            return;
        }

        // Check if the message contains a phrase that maps to a code word
        if (Translator.phraseToCodeWord.containsKey(message.toLowerCase())) {
            String codeWord = Translator.phraseToCodeWord.get(message.toLowerCase());
            translation = Translator.textToMorse(codeWord);
        } else {
            translation = Translator.textToMorse(message);
        }

        checkBoxHandler(translation); //was message variable

        int currFreq = (int) bandwidthSlider.getValue();

        for (int i = 0; i < ChatRoom.getBots().size(); i++) {
            if (currFreq == ChatRoom.getBots().get(i).getFrequency()) {
                bot = ChatRoom.getBots().get(i);
            }
        }
        if (bot == null) {
            bot = ChatRoom.getBots().get(0); //default bot
        }
        System.out.println("Bot: " + bot.getName());

            new Thread(() -> {
                ChatClient.sendMessage(newMessage.getText(), bot);
                ChatMessage lastMessage = ChatRoom.getChatMessageList().get(ChatRoom.getChatMessageList().size() - 1);

                new Thread(() -> {
                        try {
                            Thread.sleep(500);
                            String translationMessage = Translator.textToMorse(lastMessage.getText());
                            ChatMessage.playMessageSound(translationMessage, wpmSlider.getValue());
                        } catch (LineUnavailableException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                }).start();

                Platform.runLater(() -> {
                    //ChatRoom.addMessage(lastMessage);
                    addMessageToChatLogUI(lastMessage);
                });
            }).start();
    }

    @FXML public void setWPM() {WPM = wpmSlider.getValue();}

    @FXML public void setFrequency() {
        ToneGenerator.setFrequency((int) toneFrequencySlider.getValue());
    }

    public void setWhiteNoiseVolume(){
        WhiteNoise.setVolume((int) bandPassSlider.getValue());
    }

    @FXML
    public void setHertzLabel(){
        int hertz = (int) toneFrequencySlider.getValue();
        bandwidthLabel.setText(hertz + " Hz");
    }

    //Save to JSON file
    public String toJSON() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ScenarioData data = new ScenarioData(ChatRoom.getChatMessageList(), ChatRoom.getBots());
        return gson.toJson(data);
    }

    public void saveToFile(File file) {
        try {
            PrintWriter out = new PrintWriter(file);
            out.println(toJSON());
            out.close();
        } catch (Exception e) {
            System.out.println("Error saving to file " + file + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static ScenarioData loadFromJSONFile(File file) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(new FileReader(file), ScenarioData.class);
        } catch (Exception e) {
            System.out.println(" file " + file + " not found or invalid. Setting to empty instead.");
            return new ScenarioData(new ArrayList<>(), new ArrayList<>());
        }
    }

    @FXML
    private void saveCurrentScenario() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Notepad");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File choosenFile = fileChooser.showSaveDialog(RadioApp.getScene().getWindow());
        saveToFile(choosenFile);
    }

    @FXML
    private void loadNewScenario() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Notepad");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File choosenFile = fileChooser.showOpenDialog(RadioApp.getScene().getWindow());
        ScenarioData scenarioData = loadFromJSONFile(choosenFile);
        loadHelper(scenarioData);
    }

    @FXML
    public void saveScenarioToFile() {
        List<ChatMessage> chatMessages = ChatRoom.getChatMessageList();
        List<ChatBot> bots = ChatRoom.getBots();
        ScenarioData scenarioData = new ScenarioData(chatMessages, bots);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(scenarioData);

        try (PrintWriter out = new PrintWriter(DEFAULT_USER_PREFERENCES_FILE)) {
            out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadScenarioFromFile() throws FileNotFoundException {
        Gson gson = new Gson();
        ScenarioData scenarioData = gson.fromJson(new FileReader(DEFAULT_USER_PREFERENCES_FILE), ScenarioData.class);
        loadHelper(scenarioData);
    }

    private void loadHelper(ScenarioData scenarioData) {
        try {
            List<ChatMessage> chatMessages = scenarioData.getChatMessages();
            List<ChatBot> bots = scenarioData.getBots();
            ChatRoom.setChatMessageList(chatMessages);
            ChatRoom.setBots(bots);
            chatLogVBox.getChildren().clear();
            for (ChatMessage message : chatMessages) {
                addMessageToChatLogUI(message);
            }
            botListView.getItems().clear();
            botListView.getItems().addAll(ChatRoom.getBots());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}