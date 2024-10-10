package edu.augustana;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
    private static List<ChatBot> bots;
    private static List<ChatMessage> chatLogMessageList = new ArrayList<>();

    private ChatRoom() {
        bots = new ArrayList<>();
        chatLogMessageList.add(new ChatMessage("Welcome to chat room!", "System", Color.GREEN));
    }

    public static List<ChatBot> getBots() {
        return bots;
    }

    public static List<ChatMessage> getChatMessageList() {
        return chatLogMessageList;
    }

    public static void addMessage(ChatMessage message) {
        chatLogMessageList.add(message);
    }
}
