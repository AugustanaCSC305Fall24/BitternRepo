package edu.augustana;

import javax.sound.sampled.LineUnavailableException;


public class UserInput {
    private String input;
    private long lastClickTime;

    public UserInput() {
        this.lastClickTime = System.currentTimeMillis();
    }

    public String userCWInput(String sound) throws LineUnavailableException {
        char cw;
        if (sound.equalsIgnoreCase("dit")) {
            ToneGenerator.playDit(44100);
            cw = '.';
        } else {
            ToneGenerator.playDah(44100);
            cw = '-';
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < 1000) {
            input += cw;
        } else if(currentTime - lastClickTime < 2000){
            input += " " + cw;
        } else {
            input += " | " + cw;
        }
        lastClickTime = currentTime;
        return input;
    }

}



