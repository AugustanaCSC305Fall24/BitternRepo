package edu.augustana;

import javax.sound.sampled.LineUnavailableException;


public class UserInput {
    private String input;
    private long lastClickTime;

    public UserInput() {
        this.lastClickTime = System.currentTimeMillis();
    }

    public String userDitInput() throws LineUnavailableException {
        ToneGenerator.playDit(44100);
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastClickTime < 2000) {
            input += ".";
        } else if(currentTime - lastClickTime < 3000){
            input += " .";
        } else {
            input += " | .";
        }
        lastClickTime = currentTime;
        return input;
    }

    public String userDahInput() throws LineUnavailableException {
        ToneGenerator.playDah(44100);
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastClickTime < 2000) {
            input += "-";
        } else if(currentTime - lastClickTime < 3000){

            input += " -";
        } else {
            input += " | -";
        }
        lastClickTime = currentTime;
        return input;
    }

}



