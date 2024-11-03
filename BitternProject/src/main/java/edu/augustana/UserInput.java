package edu.augustana;

import javax.sound.sampled.LineUnavailableException;


public class UserInput {
    private String input;
    private long lastClickTime;

    public UserInput() {
        this.input = "";
        this.lastClickTime = System.currentTimeMillis();
    }

    public String userCWInput(String sound) throws LineUnavailableException {
        char cw;

        if (sound.equalsIgnoreCase("dit")) {
            ToneGenerator.playDit();
            cw = '.';
        } else {
            ToneGenerator.playDah();
            cw = '-';
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < 1000) {
            input += cw;
//        } else if(currentTime - lastClickTime < 3000){  "i took this out because the morse translator doesn't understand spaces".
//            input += " " + cw;
        } else {
            if (!input.isEmpty()) {
                input += " | " + cw;
            } else {
                input += cw;
            }
        }
        lastClickTime = currentTime;
        return input;
    }

    public void clearInput(Boolean isEmpty) {
        if (isEmpty) { input = "";}
    }

}



