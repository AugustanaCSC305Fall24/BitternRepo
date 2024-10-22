package edu.augustana;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class ChatMessage {
    private String text;
    private String sender;
    private Color color;
    private static List<ChatMessage> chatLogMessageList = new ArrayList<>();

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


}
