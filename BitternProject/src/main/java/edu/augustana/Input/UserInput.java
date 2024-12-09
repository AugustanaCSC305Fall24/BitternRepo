package edu.augustana.Input;

import edu.augustana.Radio.ToneGenerator;

import javax.sound.sampled.LineUnavailableException;

public class UserInput {
    private String input;
    private long lastClickTime;
    public enum Sounds {DIT, DAH}

    public UserInput() {
        this.input = "";
        this.lastClickTime = System.currentTimeMillis();
    }

    public String userCWInput(Sounds type, double wpm) throws LineUnavailableException {
        char cw;
        double delay = 1000 * (1200 - 37.2 * wpm) / (20 * wpm);
        int soundLength;

        if (type == Sounds.DIT) {
            ToneGenerator.playDit();
            cw = '.';
            soundLength = 60;
        } else {
            ToneGenerator.playDah();
            cw = '-';
            soundLength = 180;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime - soundLength < ((double) 3 /19)*delay) {
            input += cw;
        } else if(currentTime - lastClickTime - soundLength < ((double) 7 /19)*delay){
            input += " " + cw;
        } else if (!input.isEmpty()) {
                input += " | " + cw;
        } else {
                input += cw;
        }
        lastClickTime = currentTime;
        return input;
    }

    public void clearInput(Boolean isEmpty) {
        if (isEmpty) { input = "";}
    }

}



