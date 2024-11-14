package edu.augustana;

import javafx.beans.binding.Bindings;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.LineUnavailableException;



class ToneGeneratorTest {

    @Test
    void testPlayDit() throws LineUnavailableException {

    }


    @Test
    void testPlayDah() throws LineUnavailableException {
        ToneGenerator.playDah();
    }


}