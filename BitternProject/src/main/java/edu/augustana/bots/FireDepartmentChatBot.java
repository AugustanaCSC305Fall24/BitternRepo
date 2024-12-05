package edu.augustana.bots;

import javafx.scene.paint.Color;

public class FireDepartmentChatBot extends ChatBot {

    public FireDepartmentChatBot(String name, Color textColor, double value) {
        super(name, textColor, value);
    }

    @Override
    public String getPersonalityType() {
        return "Friendly";
    }

    @Override
    String generateNewMessageText() {
        return "What fun! Let's talk about " + getRandomTopic() + "!";
    }

    @Override
    String generateCommentAbout(String word) {
        return "Oooh - " + word + ", that's so great!  Tell me more!";
    }

    @Override
    String generateResponseToSender(String sender) {
        return "I love you " + sender + ", you're the best!";
    }

//    @Override
//    public ChatMessage generateResponseMessage(ChatMessage previousMessage) {
//        String responseText;
//        responseText = "";
//        return new ChatMessage(responseText,getName(), getTextColor());
//
//    }
}
