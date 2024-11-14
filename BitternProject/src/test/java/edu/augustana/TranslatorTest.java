package edu.augustana;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TranslatorTest {

    @Test
    void testTextToMorse() {
        String text = "hello world";
        assertEquals(".... . .-.. .-.. --- | .-- --- .-. .-.. -..", Translator.textToMorse(text));
    }

    @Test
    void TestMorseToText() {
        String text = ".... . .-.. .-.. --- | .-- --- .-. .-.. -..";
        assertEquals("hello world", Translator.morseToText(text));
    }
}