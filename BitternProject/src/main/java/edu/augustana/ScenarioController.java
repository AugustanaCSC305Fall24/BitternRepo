package edu.augustana;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.List;

public class ScenarioController {

    @FXML
    private Slider volumeSlider;

    @FXML
    private Label frequencyLabel;

    @FXML private ScrollPane chatLogScrollPane;

    @FXML private VBox chatLogVBox;

    @FXML private Button dahButton;

    @FXML private Button ditButton;

    @FXML
    private CheckBox translationCheckbox;

    @FXML private TextField userMessageTextField;

    @FXML private Label userNameLabel;

    @FXML private Button welcomeButton;

    @FXML private Slider bandWidthSlider;

    @FXML private Slider frequencySlider;

    private long lastClickTime = 0;

    private String input = "";



    @FXML private CheckBox englishCheckBox;

    @FXML
    private void setFrequencyLabel() {
        frequencyLabel.setText("Frequency: " + (int) frequencySlider.getValue() + " Hz");
    }


    @FXML
    private void switchToWelcome(ActionEvent event) throws IOException {
        Tone.line.drain();
        Tone.line.close();
        RadioApp.setRoot("WelcomeScreen");
    }

    @FXML
    private void volumeControl(){
        volumeSlider.setValue(50);

    }

    // got from exam 1 chatbots
    @FXML
    private void clearChatLogAction() {
        List<ChatMessage> messages = ChatRoom.getChatMessageList();  //ChatterBoxApp.getCurrentRoom().getChatMessageList();
        if (messages == null || messages.isEmpty()) {
            return; // or handle the null/empty case appropriately
        }

        ChatMessage firstSystemPost = messages.get(0);
        messages.clear();
        chatLogVBox.getChildren().clear();
        messages.add(firstSystemPost);
        addMessageToChatLogUI(firstSystemPost);
    }

    @FXML
    private void sendAction() throws LineUnavailableException {
        String msgText = userMessageTextField.getText();
        String translation = "";
        if (!msgText.isBlank()) {
            ChatMessage newMessageFromUser = new ChatMessage(msgText, "User", Color.BLACK);
            ChatRoom.addMessage(newMessageFromUser);
            addMessageToChatLogUI(newMessageFromUser);



            if (translationCheckbox.isSelected()) {

                if (englishCheckBox.isSelected()) {
                    // Translate text to Morse code
                     translation = MorseCodeTranslator.textToMorse(msgText);
                    if (translation.isEmpty()) {
                        translation = "Empty english translation";
                    }
                    ChatMessage newMessageFromTranslator = new ChatMessage(translation, "Translator", Color.RED);
                    addMessageToChatLogUI(newMessageFromTranslator);





                } else {
                    // Translate Morse code to text
                    translation = MorseCodeTranslator.morseToText(msgText);
                    if (translation.isEmpty()) {
                        translation = "Invalid Morse Code";
                    }
                    ChatMessage newMessageFromTranslator = new ChatMessage(translation, "Translator", Color.GREEN);
                    addMessageToChatLogUI(newMessageFromTranslator);

                }
                playMessageSound(translation);
            }
            // Clear the input field and reset the input string
            userMessageTextField.clear();
            input = "";

            Tone.line.drain();
            Tone.line.close();

            Tone.line.open(Tone.af, Note.SAMPLE_RATE);
            Tone.line.start();

        }
    }


    private void playMessageSound(String message) throws LineUnavailableException {
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c == '.') {
                playDotSound();

            } else if (c == '-') {
                playDashSound();
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
    private void userDitInput() throws LineUnavailableException {
        String msgText = ".";
        long currentTime = System.currentTimeMillis();
        playDotSound();

        if (currentTime - lastClickTime < 2000) {
            input += ".";
        } else if(currentTime - lastClickTime < 3000){
            input += " ";
            input += ".";
        } else {
            input += " ";
            input += "|";
            input += " ";
            input += ".";
        }
        //SoundClass.playDot();

        userMessageTextField.setText(input);
        lastClickTime = currentTime;


    }

    @FXML
    private void userDahInput() throws LineUnavailableException {
        String msgText = "-";
        long currentTime = System.currentTimeMillis();
        playDashSound();

        if (currentTime - lastClickTime < 2000) {
            input += "-";
        } else if(currentTime - lastClickTime < 3000){

            input += " ";
            input += "-";
        } else {
            input += " ";
            input += "|";
            input += " ";
            input += "-";
        }
        //SoundClass.playDash();

        lastClickTime = currentTime;
        userMessageTextField.setText(input);
    }

    @ FXML
    private void controlVolume(){

    }

//    @FXML
//    void playDashSound() {
//        // Path to your dash sound file (make sure to provide the correct path)
//        String dashSoundPath = getClass().getResource("/Sound/dash.wav").toExternalForm();
//        SoundClass.playSound(dashSoundPath);
//    }
//
//    @FXML
//    void playDotSound() {
//        // Path to your dot sound file (make sure to provide the correct path)
//        String dotSoundPath = getClass().getResource("/Sound/dot.wav").toExternalForm();
//        SoundClass.playSound(dotSoundPath);
//    }

    @FXML
    void playDotSound() throws LineUnavailableException {
        Tone.play(Tone.SoundType.DOT);
    }
    @FXML
    void playDashSound() throws LineUnavailableException {
        Tone.play(Tone.SoundType.DASH);
    }
}
