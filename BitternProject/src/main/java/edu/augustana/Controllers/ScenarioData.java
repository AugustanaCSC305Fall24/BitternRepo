package edu.augustana.Controllers;

import edu.augustana.bots.ChatBot;
import edu.augustana.Chat.ChatMessage;
import java.util.List;

//This class is used to store the data for a scenario and assist in
//     the serialization and deserialization of the data. Essentially,
//     to help convert data into a JSON format and back.
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