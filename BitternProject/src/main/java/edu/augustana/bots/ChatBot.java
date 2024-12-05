package edu.augustana.bots;

import edu.augustana.Chat.ChatMessage;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public abstract class ChatBot {
    private final String name;
    private final Color textColor;
    private double frequency = 0;

    private static final Random randomGen = new Random();

    public ChatBot(String name, Color textColor, double frequency) {
        this.name = name;
        this.textColor = textColor;
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }
    public Color getTextColor() {
        return textColor;
    }

    @Override
    public String toString() {
        return name +"   "+ "Frequency:" +"  "+ frequency;
    }

    abstract String getPersonalityType();

    public ChatMessage generateNewMessage() {
        return new ChatMessage(generateNewMessageText(), name, textColor);
    }

    public int getFrequency() {
        return (int) frequency;
    }

    public ChatMessage generateResponseMessage(ChatMessage previousMessage) {
//        String responseText;
//        if (randomGen.nextDouble() < 0.5) {
//            responseText = generateResponseToContent(previousMessage.getText());
//        } else {
//            responseText = generateResponseToSender(previousMessage.getSender());
//        }
//        return new ChatMessage(responseText, name, textColor);
        String responseText;
        responseText = generateResponseToContent(previousMessage.getText());
        return new ChatMessage(responseText, name, textColor);
    }

    abstract String generateNewMessageText();

    String getRandomTopic() {
        String[] topics = {"the weather", "Star Wars", "your mother", "the best vacation spot",
                "life's treasures", "classes you've failed", "the world's worst CSC 305 professor",
                "the best way to cook pasta", "Lana wrestling gators", "defining your aura",
                "who won the 2020 election", "the best way to make a grilled cheese sandwich",
                "banning Tik Tok", "death metal bands", "organized crime", "your hair"};
        return topics[randomGen.nextInt(topics.length)];
    }

    private String generateResponseToContent(String incomingMessage) {
        return generateCommentAbout(selectRandomLongWord(incomingMessage));
    }
    private String selectRandomLongWord(String incomingMessage) {
        String[] words = incomingMessage.split("\\W+");
        if (words.length > 0) {
            Arrays.sort(words, Comparator.comparingInt(String::length)); // sort by length
            // randomly pick a longer word (from the last 1/6 of the list)
            int startIndex = words.length * 5 / 6;
            int randomIndex = startIndex + randomGen.nextInt(words.length - startIndex);
            return words[randomIndex];
        } else {
            return "nothing";
        }
    }
    abstract String generateCommentAbout(String word);

    abstract String generateResponseToSender(String sender);

    public static String getRandomBotName() {
        String[] names = {"Alice", "Bubba", "Candy", "Doodles", "Egbert", "Fifi", "Gus", "Holly", "Iggy",
                "Jasper", "Kiki", "Lulu", "Mimi", "Noodles", "Oscar", "Penny", "Quincy", "Rufus", "Sally",
                "Toby", "Ursula", "Violet", "Wally", "Xander", "Yolanda", "Zelda"};
        String[] adjectives = {"Awesome", "Bodacious", "Clunker", "Dude", "Eery", "Funky", "Goosey", "Happy",
                "Hippy", "Irritable", "Jolly", "Kooky", "Lunker", "Messy", "Nut", "Optometrist", "Punky",
                "Quirky", "Rumpled", "Snarky", "Tree", "Unknown", "Vixen", "Wonk", "Xenial", "Yummy",
                "Zany"};
        String name =names[randomGen.nextInt(names.length)];
        String adjective = adjectives[randomGen.nextInt(adjectives.length)];
        return  name + " the " + adjective;
    }

    public Color getColor() {
        return textColor;
    }
}
