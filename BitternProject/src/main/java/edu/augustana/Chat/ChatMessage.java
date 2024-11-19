package edu.augustana.Chat;

import edu.augustana.Radio.ToneGenerator;
import javafx.scene.paint.Color;

import javax.sound.sampled.LineUnavailableException;
import java.util.ArrayList;
import java.util.List;

public class ChatMessage {
    private String text;
    private String sender;
    private Color color;
    private static List<ChatMessage> chatLogMessageList = new ArrayList<>();
    private static Boolean messagePlaying = false;

    public ChatMessage(String text, String sender, Color color) {
        this.text = text;
        this.sender = sender;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }

    public Color getColor() {
        return color;
    }

    public static List<ChatMessage> getChatMessageList() {return chatLogMessageList;}

    public static void addMessage(ChatMessage message) {
        chatLogMessageList.add(message);
    }

    public static void playMessageSound(String message, double wpm) throws LineUnavailableException, InterruptedException {
        while (messagePlaying) Thread.sleep(100);

        double delay = 1000 * (1200 - 37.2 * wpm) / (20 * wpm);

        messagePlaying = true;
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c == '.') {
                ToneGenerator.playDit();
            } else if (c == '-') {
                ToneGenerator.playDah();
            } else if (c == ' ') {
                ToneGenerator.playSilence(((double) 3 / 19) * delay / 1000);
            } else if (c == '|') {
                ToneGenerator.playSilence(((double) 7 / 19) * delay / 1000);
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


}
