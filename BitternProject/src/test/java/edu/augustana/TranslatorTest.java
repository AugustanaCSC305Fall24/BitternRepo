package edu.augustana;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TranslatorTest {

    @Test
    void testTextToMorse() {
        assertEquals(".... . .-.. .-.. ---", Translator.textToMorse("hello"));
        assertEquals(".... . .-.. .-.. --- | .-- --- .-. .-.. -..", Translator.textToMorse("hello world"));
        assertEquals("Empty english translation", Translator.textToMorse(""));

    }

    @Test
    void TestMorseToText() {
        assertEquals("hello", Translator.morseToText(".... . .-.. .-.. ---"));
        assertEquals("hello world", Translator.morseToText(".... . .-.. .-.. --- | .-- --- .-. .-.. -.."));
        assertEquals("Invalid Morse Code", Translator.morseToText(""));
    }
}