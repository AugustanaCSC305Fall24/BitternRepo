package edu.augustana.Controllers;

import edu.augustana.bots.ChatBot;
import edu.augustana.Chat.ChatMessage;
import java.util.List;

public class ScenarioData {
    private List<ChatMessage> chatMessages;
    private List<ChatBot> bots;

    public ScenarioData(List<ChatMessage> chatMessages, List<ChatBot> bots) {
        this.chatMessages = chatMessages;
        this.bots = bots;
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public List<ChatBot> getBots() {
        return bots;
    }

    public void setBots(List<ChatBot> bots) {
        this.bots = bots;
    }
}