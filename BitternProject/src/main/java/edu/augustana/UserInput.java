package edu.augustana;

import javax.sound.sampled.LineUnavailableException;


public class UserInput {
    private String input;
    private long lastClickTime;

    public UserInput() {
        this.lastClickTime = System.currentTimeMillis();
    }

    public String userCWInput(String sound) throws LineUnavailableException {
        if (sound.equalsIgnoreCase("dit")) {
            ToneGenerator.playDit(44100);
        } else {
            ToneGenerator.playDah(44100);
        }
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastClickTime < 1000) {
            input += sound;
        } else if(currentTime - lastClickTime < 2000){
            input += " " + sound;
        } else {
            input += " | " + sound;
        }
        lastClickTime = currentTime;
        return input;
    }

}



