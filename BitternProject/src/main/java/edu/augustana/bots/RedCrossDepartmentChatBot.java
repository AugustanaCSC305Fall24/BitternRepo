package edu.augustana.bots;

import edu.augustana.Chat.ChatMessage;
import javafx.scene.paint.Color;

public class RedCrossDepartmentChatBot extends ChatBot {

    public RedCrossDepartmentChatBot(String name, Color textColor) {
        super(name, textColor);
    }

    @Override
    public String getPersonalityType() {
        return "Mean";
    }

    @Override
    String generateNewMessageText() {
        return "Seriously, you annoy me. Who wants to discuss " + getRandomTopic() + "?";
    }

    @Override
    String generateCommentAbout(String word) {
        return "Ugh, " + word + " makes me want to vomit.  Log off you noob!";
    }

    @Override
    String generateResponseToSender(String sender) {
        return "I'm not speaking to " + sender + " anymore.  Such a loser.";
    }

//    @Override
//    public ChatMessage generateResponseMessage(ChatMessage previousMessage) {
//        String responseText;
//        responseText = "";
//        return new ChatMessage(responseText,getName(), getTextColor());
//
//    }
}
