package edu.augustana.Input;

import java.util.HashMap;
import java.util.Map;

public class Translator {

    public static final Map<Character, String> charToMorse = new HashMap<>();
    private static final Map<String, Character> morseToChar = new HashMap<>();
    public static final Map<String, String> phraseToCodeWord = new HashMap<>();

    static {
        phraseToCodeWord.put("help", "SOS");
        phraseToCodeWord.put("Good afternoon", "GA");
        phraseToCodeWord.put("Goodbye", "73");
        // Add more mappings later
    }

    public static char[] englishLetters
            = {'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', ' ', '0', '1', '2',
            '3', '4', '5', '6', '7', '8', '9'};

    // Morse code by indexing
    public static String[] morseCodeLetters
            = { ".-",   "-...", "-.-.", "-..",  ".",
            "..-.", "--.",  "....", "..",   ".---",
            "-.-",  ".-..", "--",   "-.",   "---",
            ".--.", "--.-", ".-.",  "...",  "-",
            "..-",  "...-", ".--",  "-..-", "-.--",
            "--..", "|", "-----", ".----", "..---",
            "...--", "....-", ".....", "-....",
            "--...", "---..", "----."};

    public static String[] codeWords
            = { "CQ", "GM", "GA", "GE", "GN",
            "AGN", "QSB", "QRS", "QRQ", "WPM",
            "SOS", "R", "TU", "73", "K"};

    public static String[] codeWordTranslation
            = { "Calling all stations", "Good morning", "Good afternoon", "Good evening", "Good night",
            "Again", "Fading signal", "Send slower", "Send faster", "Words per minute",
            "Help", "Roger", "Thank you", "Best wishes", "Over"};

    public Translator() {
        // Initialize the maps with Morse code mappings
        for (int i = 0; i < englishLetters.length; i++) {
            charToMorse.put(englishLetters[i], morseCodeLetters[i]);
        }
        for (int i = 0; i < morseCodeLetters.length; i++) {
            morseToChar.put(morseCodeLetters[i], englishLetters[i]);
        }
    }

    public static String textToMorse(String text) {
        new Translator();
        StringBuilder morse = new StringBuilder();
        for (char c : text.toLowerCase().toCharArray()) {
            if (charToMorse.containsKey(c)) {
                morse.append(charToMorse.get(c)).append(" ");
            }
        }
        String translation = morse.toString().trim();
        if (translation.isEmpty()) {
            return "Empty english translation";
        } else {
            return translation;
        }
    }

    public static String morseToText(String morse) {
        new Translator();
        StringBuilder text = new StringBuilder();
        for (String code : morse.split(" ")) {
            if (morseToChar.containsKey(code)) {
                text.append(morseToChar.get(code));
            }
        }
        String translation = text.toString();
        if (translation.isEmpty()) {
            return "Invalid Morse Code";
        } else {
            return translation;
        }
    }

    // Testing the translator
    public static void main(String[] args) {
        Translator translator = new Translator();
        String text = "Hello, World!";
        String morse = translator.textToMorse(text);
        System.out.println("Text: " + text);
        System.out.println("Morse: " + morse);
        System.out.println("Decoded: " + translator.morseToText(morse));
    }
}