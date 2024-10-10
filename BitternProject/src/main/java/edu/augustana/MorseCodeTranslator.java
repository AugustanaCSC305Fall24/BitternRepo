package edu.augustana;

import java.lang.module.ModuleReader;
import java.util.HashMap;
import java.util.Map;

public class MorseCodeTranslator {

    private static final Map<Character, String> charToMorse = new HashMap<>();
    private static final Map<String, Character> morseToChar = new HashMap<>();

    private static char[] letter = { 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', ' '};

    // Morse code by indexing
    private static String[] code
            = { ".-",   "-...", "-.-.", "-..",  ".",
            "..-.", "--.",  "....", "..",   ".---",
            "-.-",  ".-..", "--",   "-.",   "---",
            ".--.", "--.-", ".-.",  "...",  "-",
            "..-",  "...-", ".--",  "-..-", "-.--",
            "--..", "|"};

    private static String[] codeWords
            = { "CQ", "GM", "GA", "GE", "GN",
            "AGN", "QSB", "QRS", "QRQ", "WPM",
            "R", "TU", "73"};

    private static String[] translateCodeWords
            = { "Calling all stations", "Good morning", "Good afternoon", "Good evening", "Good night",
            "Again", "Fading signal", "Send slower", "Send faster", "Words per minute",
            "Roger", "Thank you", "Best wishes"};

    public MorseCodeTranslator() {
        // Initialize the maps with Morse code mappings
        for (int i = 0; i < letter.length; i++) {
            charToMorse.put(letter[i], code[i]);
        }
        for (int i = 0; i < code.length; i++) {
            morseToChar.put(code[i], letter[i]);
        }
    }

    public static String textToMorse(String text) {
        StringBuilder morse = new StringBuilder();
        for (char c : text.toLowerCase().toCharArray()) {
            if (charToMorse.containsKey(c)) {
                morse.append(charToMorse.get(c)).append(" ");
            }
        }
        return morse.toString().trim();
    }

    public static String morseToText(String morse) {
        new MorseCodeTranslator();
        StringBuilder text = new StringBuilder();
        for (String code : morse.split(" ")) {
            if (morseToChar.containsKey(code)) {
                text.append(morseToChar.get(code));
            }
        }
        if (text.length() == 0) {
            return "nothing";
        }
        return text.toString();
    }

    public static void main(String[] args) {
        MorseCodeTranslator translator = new MorseCodeTranslator();
        String text = "Hello, World!";
        String morse = translator.textToMorse(text);
        System.out.println("Text: " + text);
        System.out.println("Morse: " + morse);
        System.out.println("Decoded: " + translator.morseToText(morse));
    }
}