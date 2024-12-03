package edu.augustana.bots;

import edu.augustana.Chat.ChatMessage;
import javafx.scene.paint.Color;

public class VictimChatBot extends ChatBot {

    public VictimChatBot(String name, Color textColor) {
        super(name, textColor);
    }

    @Override
    String getPersonalityType() {
        return "Pirate";
    }

    @Override
    String generateNewMessageText() {
        return "Ahoy!  Best be chattin' bout " + getRandomTopic() + " or ye'll be walkin' the plank!";
    }

    @Override
    String generateCommentAbout(String word) {
        return "Arrr, don't try to tell me about " + word + ", matey!";
    }

    @Override
    String generateResponseToSender(String sender) {
        return "Avast, " + sender + " be gettin' their sea legs soon!";
    }

//    @Override
//    public ChatMessage generateResponseMessage(ChatMessage previousMessage) {
//        String responseText;
//        responseText = "";
//        return new ChatMessage(responseText,getName(), getTextColor());
//
//    }
}
