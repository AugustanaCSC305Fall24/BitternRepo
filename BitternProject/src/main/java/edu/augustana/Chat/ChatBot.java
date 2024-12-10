package edu.augustana.Chat;

import javafx.scene.paint.Color;

import java.util.*;

public class ChatBot {
    private final String name;
    private final transient Color textColor;

    private double frequency = 0;
    private String personalityType;

    private ArrayList<ChatMessage> chatLog = new ArrayList<>();

    public ChatBot(String name,String personalityType, Color textColor, double frequency) {
        this.name = name;
        this.textColor = textColor;
        this.frequency = frequency;
        this.personalityType = personalityType;

    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name +"   "+ "Frequency:" +"  "+ frequency;
    }

    public int getFrequency() {
        return (int) frequency;
    }

    public ArrayList<ChatMessage> getChatLog() {
        return chatLog;
    }

    public Color getColor() {
        return textColor;
    }

}
