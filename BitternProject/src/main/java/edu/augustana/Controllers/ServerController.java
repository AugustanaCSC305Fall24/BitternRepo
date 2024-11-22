package edu.augustana.Controllers;

import com.google.gson.Gson;
import edu.augustana.Chat.ChatMessage;
import edu.augustana.Radio.RadioApp;
import edu.augustana.Radio.ToneGenerator;
import edu.augustana.Radio.WhiteNoise;
import jakarta.websocket.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

@ClientEndpoint
public class ServerController extends Controller implements Initializable {

    @FXML private Button welcomeButton;
    @FXML private Button helpPageButton;
    @FXML private Button notepadButton;
    @FXML private Button repeatSoundButton;
    @FXML private Label replyLabel;
    @FXML private TextField replyTextbox;
    @FXML private TextField replyTranslationTextbox;
    @FXML private Button sendMessage;
    @FXML private TextField sendMessageTextbox;
    @FXML private TextField sendTranslationTextbox;
    @FXML private CheckBox translationCheckbox;
    @FXML private Slider frequencySlider;
    @FXML private Slider staticSlider;
    @FXML private Slider wpmSlider;

    private final String USER_NAME = "Penguin";
    private Session session;

    public void initialize(URL arg0, ResourceBundle arg1){
        userText = sendMessageTextbox;
        new Thread(whiteNoise::play).start();
        connect();
    }

    @FXML
    void openHelpScreen(ActionEvent event) {
        RadioApp.createNewWindow("HelpPageScreen", "Help Page");
    }

    @FXML
    void openNotepad(ActionEvent event){
        RadioApp.createNewWindow("NotepadScreen", "Notepad");
    }

    @FXML
    void openHowTo(ActionEvent event) {
        RadioApp.createNewWindow("HowToScreen", "How To Page");
    }

    // Method to switch to the Welcome screen
    @FXML
    private void switchToWelcome(ActionEvent event) throws IOException {
        whiteNoise.exit();
        RadioApp.setRoot("WelcomeScreen");
    }

    @Override
    public void sendAction() throws LineUnavailableException {
        // Here for send to server
        translateMessage(userText.getText(), sendTranslationTextbox);
        ChatMessage chatMessage = new ChatMessage(userText.getText(), USER_NAME, Color.RED);
//        System.out.println(chatMessage.getSender() + chatMessage.getText());
//        replyTextbox.setText(chatMessage.getText());
        Gson gson = new Gson();
        String jsonText = gson.toJson(chatMessage);
        System.out.println("Sending WebSocket message: " + jsonText);
        session.getAsyncRemote().sendText(jsonText);
        super.sendAction();
    }

    public void translateMessage(String msgText, TextField textBox){
        textBox.clear();
        textBox.setText(edu.augustana.Input.Translator.morseToText(msgText));
    }

    public void setWPM() {WPM = wpmSlider.getValue();}

    public void setFrequency() {
        ToneGenerator.setFrequency((int) frequencySlider.getValue());
    }

    public void setWhiteNoiseVolume(){
        WhiteNoise.setVolume((int) staticSlider.getValue());
    }


    public void stop() {
        try {
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String jsonMessage) {
        System.out.println("Received WebSocket message: " + jsonMessage);
        ChatMessage chatMessage = new Gson().fromJson(jsonMessage, ChatMessage.class);
        replyTextbox.setText(chatMessage.getText());
        translateMessage(replyTextbox.getText(), replyTranslationTextbox);
    }

    private void connect() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(this, new URI("ws://34.41.147.186:8000/ws/penguin"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
