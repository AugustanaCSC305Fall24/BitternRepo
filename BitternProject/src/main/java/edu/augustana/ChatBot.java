package edu.augustana;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class ChatBot {
    private final String name;
    private final String personalityType;
    private final Color textColor;

    public static final String[] PERSONALITY_TYPES = {"Friendly", "Mean", "Pirate", "Dog"};
    private static final Random randomGen = new Random();

    public ChatBot(String name, String personalityType, Color textColor) {
        this.name = name;
        this.personalityType = personalityType;
        this.textColor = textColor;
    }

    public String getName() {
        return name;
    }
    public Color getTextColor() {
        return textColor;
    }

    @Override
    public String toString() {
        return name + " [" + personalityType + "]";
    }

    public ChatMessage generateNewMessage() {
        return new ChatMessage(generateNewMessageText(), name, textColor);
    }

    public ChatMessage generateResponseMessage(ChatMessage previousMessage) {
        String responseText;
        if (randomGen.nextDouble() < 0.5) {
            responseText = generateResponseToContent(previousMessage.getText());
        } else {
            responseText = generateResponseToSender(previousMessage.getSender());
        }
        return new ChatMessage(responseText, name, textColor);
    }

    private String generateNewMessageText() {
        if (personalityType.equals("Friendly")) {
            return "What fun! Let's talk about " + getRandomTopic() + "!";
        } else if (personalityType.equals("Mean")) {
            return "Seriously, you annoy me. Who wants to discuss " + getRandomTopic() + "?";
        } else if (personalityType.equals("Pirate")) {
            return "Ahoy!  Best be chattin' bout " + getRandomTopic() + " or ye'll be walkin' the plank!";
        } else if (personalityType.equals("Dog")) {
            return dogWoofString();
        } else {
            throw new IllegalStateException("Invalid personality type: " + personalityType);
        }
    }

    private String getRandomTopic() {
        String[] topics = {"the weather", "Star Wars", "your mother", "the best vacation spot",
                "life's treasures", "classes you've failed", "the world's worst CSC 305 professor",
                "the best way to cook pasta", "Lana wrestling gators", "defining your aura",
                "who won the 2020 election", "the best way to make a grilled cheese sandwich",
                "banning Tik Tok", "death metal bands", "organized crime", "your hair"};
        return topics[randomGen.nextInt(topics.length)];
    }

    private String generateResponseToContent(String incomingMessage) {
        return " "; //generateCommentAbout(selectRandomLongWord(incomingMessage));
    }
//    private String selectRandomLongWord(String incomingMessage) {
//        String[] words = incomingMessage.split("\\W+");
//        if (words.length > 0) {
//            Arrays.sort(words, Comparator.comparingInt(String::length)); // sort by length
//            // randomly pick a longer word (from the last 1/6 of the list)
//            return words[randomGen.nextInt(words.length * 5 / 6, words.length)];
//        } else {
//            return "nothing";
//        }
//    }

    private String generateCommentAbout(String word) {
        word = word.toUpperCase();
        if (personalityType.equals("Friendly")) {
            return "Oooh - " + word + ", that's so great!  Tell me more!";
        } else if (personalityType.equals("Mean")) {
            return "Ugh, " + word + " makes me want to vomit.  Log off you noob!";
        } else if (personalityType.equals("Pirate")) {
            return "Arrr, don't try to tell me about " + word + ", matey!";
        } else if (personalityType.equals("Dog")) {
            return dogWoofString();
        } else {
            throw new IllegalStateException("Invalid personality type: " + personalityType);
        }
    }

    private String generateResponseToSender(String sender) {
        sender = sender.split("\\W")[0]; // just use the first word of the sender's name
        if (personalityType.equals("Friendly")) {
            return "I love you " + sender + ", you're the best!";
        } else if (personalityType.equals("Mean")) {
            return "I'm not speaking to " + sender + " anymore.  Such a loser.";
        } else if (personalityType.equals("Pirate")) {
            return "Avast, " + sender + " be gettin' their sea legs soon!";
        } else if (personalityType.equals("Dog")) {
            return dogWoofString();
        } else {
            throw new IllegalStateException("Invalid personality type: " + personalityType);
        }
    }

    private String dogWoofString(){
        int randNum = randomGen.nextInt(6 - 2 + 1) + 2;
        String woofString = "";
        for (int i = 0; i < randNum; i++){
            woofString += "woof ";
        }
        return woofString;
    }

    public static String getRandomBotName() {
        String[] names = {"Alice", "Bubba", "Candy", "Doodles", "Egbert", "Fifi", "Gus", "Holly", "Iggy",
                "Jasper", "Kiki", "Lulu", "Mimi", "Noodles", "Oscar", "Penny", "Quincy", "Rufus", "Sally",
                "Toby", "Ursula", "Violet", "Wally", "Xander", "Yolanda", "Zelda", "DogBot"};
        String[] adjectives = {"Awesome", "Bodacious", "Clunker", "Dude", "Eery", "Funky", "Goosey", "Happy",
                "Hippy", "Irritable", "Jolly", "Kooky", "Lunker", "Messy", "Nut", "Optometrist", "Punky",
                "Quirky", "Rumpled", "Snarky", "Tree", "Unknown", "Vixen", "Wonk", "Xenial", "Yummy",
                "Zany"};
        String name =names[randomGen.nextInt(names.length)];
        String adjective = adjectives[randomGen.nextInt(adjectives.length)];
        return  name + " the " + adjective;
    }
}
