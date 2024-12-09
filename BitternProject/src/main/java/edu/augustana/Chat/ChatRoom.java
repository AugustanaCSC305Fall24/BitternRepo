package edu.augustana.Chat;

import edu.augustana.bots.ChatBot;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
    private String roomTitle;
    private static List<ChatBot> bots= new ArrayList<>();
    private static List<ChatMessage> chatLogMessageList = new ArrayList<>();

    public ChatRoom(String roomTitle) {
        this.roomTitle = roomTitle;
//        chatLogMessageList = new ArrayList<>();
        chatLogMessageList.add(new ChatMessage("Welcome to " +roomTitle+"!", "System", Color.GREEN));
    }

    public static void setChatMessageList(List<ChatMessage> chatMessages) {
        chatLogMessageList = chatMessages;
    }

    public static void setBots(List<ChatBot> newBots) {
        bots = newBots;
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
