package edu.augustana;

import javafx.scene.paint.Color;

public class ChatMessage {
    private String text;
    private String sender;
    private Color color;

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

    @Override
    public String toString() {
        return "ChatMessage{" +
                "text='" + text + '\'' +
                ", sender='" + sender + '\'' +
                ", color=" + color +
                '}';
    }
}
