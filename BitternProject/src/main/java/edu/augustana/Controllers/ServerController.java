package edu.augustana.Controllers;

import com.google.gson.Gson;
import edu.augustana.Chat.ChatMessage;
import edu.augustana.Input.Translator;
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
import java.util.Random;
import java.util.ResourceBundle;

@ClientEndpoint
public class ServerController extends Controller implements Initializable {

    @FXML private TextField replyTextbox;
    @FXML private TextField replyTranslationTextbox;
    @FXML private TextField sendMessageTextbox;
    @FXML private TextField sendTranslationTextbox;
    @FXML private TextField callSignTextbox = new TextField();
    @FXML private Slider frequencySlider;
    @FXML private Slider staticSlider;
    @FXML private Slider wpmSlider;
    @FXML private Slider bandPassSlider;

    private String callSign;
    private Session session;
    private Random randGen = new Random();

    public void initialize(URL arg0, ResourceBundle arg1){
        userText = sendMessageTextbox;
        new Thread(whiteNoise::play).start();

        bandPassSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double minInput = 7.000;  // Minimum slider value
            double maxInput = 7.067;  // Maximum slider value
            int minOutput = -80;      // Minimum volume (mute) -80 dB
            int maxOutput = 6;        // Maximum volume (loud)

            double sliderValue = newValue.doubleValue(); // Current slider value
            int volumeLevel = (int) ((sliderValue - minInput) / (maxInput - minInput) * (maxOutput - minOutput) + minOutput);

            WhiteNoise.setVolume(volumeLevel); // Adjust white noise volume
        });

        generateCallSign();
        callSignTextbox.setText(callSign);
        connect();
    }

    private void generateCallSign() {
        callSign = "K9";
        for (int i = 0; i < 4; i++) {
            int index = randGen.nextInt(37);
            if (index != 26) { // skip if you get a space bar
                callSign += String.valueOf(Translator.englishLetters[index]).toUpperCase();
            }
        }
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

    @FXML
    private void switchToWelcome(ActionEvent event) throws IOException {
        WhiteNoise.reset(); // Reset white noise to default settings
        WhiteNoise.stopPlaying(); // Stop playing white noise
        RadioApp.setRoot("WelcomeScreen");
    }

    @Override
    public void sendAction() throws LineUnavailableException {
        // Here for send to server
        translateMessage(userText.getText(), sendTranslationTextbox);
        ChatMessage chatMessage = new ChatMessage(userText.getText(), callSign, Color.RED);
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

    @FXML
    public void repeatButton() throws LineUnavailableException, InterruptedException {
        ChatMessage.playMessageSound(replyTextbox.getText(), wpmSlider.getValue());
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
    public void onMessage(String jsonMessage) throws LineUnavailableException, InterruptedException {
        System.out.println("Received WebSocket message: " + jsonMessage);
        ChatMessage chatMessage = new Gson().fromJson(jsonMessage, ChatMessage.class);
        replyTextbox.setText(chatMessage.getText());
        translateMessage(replyTextbox.getText(), replyTranslationTextbox);
        ChatMessage.playMessageSound(replyTextbox.getText(), wpmSlider.getValue());
    }

    private void connect() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(this, new URI("ws://34.41.147.186:8000/ws/" + callSign));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
