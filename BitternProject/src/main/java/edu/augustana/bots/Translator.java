package edu.augustana.bots;

import java.util.HashMap;
import java.util.Map;

public class Translator {

    public static final Map<Character, String> charToMorse = new HashMap<>();
    private static final Map<String, Character> morseToChar = new HashMap<>();

    public static char[] englishLetters
            = {'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', ' ', '0', '1', '2',
            '3', '4', '5', '6', '7', '8', '9'};

    // Morse code by indexing
    public static String[] morseCodeLetters
            = {".-", "-...", "-.-.", "-..", ".",
            "..-.", "--.", "....", "..", ".---",
            "-.-", ".-..", "--", "-.", "---",
            ".--.", "--.-", ".-.", "...", "-",
            "..-", "...-", ".--", "-..-", "-.--",
            "--..", "|", "-----", ".----", "..---",
            "...--", "....-", ".....", "-....",
            "--...", "---..", "----."};




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
}

