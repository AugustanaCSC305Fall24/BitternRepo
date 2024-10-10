package edu.augustana;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UserInput {
    private String input;
    private long lastClickTime = 0;
    private String output;

    public UserInput() {

        this.lastClickTime = System.currentTimeMillis();
    }

    public String getInput() {
        Button dit = new Button();
        dit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastClickTime < 2000) {
                    input += ".";
                } else if(currentTime - lastClickTime < 3000){
                    input += " ";
                    input += ".";
                } else {
                    input += " ";
                    input += "|";
                    input += " ";
                }


                lastClickTime = currentTime;
            }
        });

        Button send = new Button();


        send.addActionListener(e -> setOutput(MorseCodeTranslator.textToMorse(input)));


        Button dah = new Button();
        dah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                long currentTime = System.currentTimeMillis();

                if (currentTime - lastClickTime < 2000) {
                    input += "-";
                } else if(currentTime - lastClickTime < 3000){

                    input += " ";
                    input += "-";
                } else {
                    input += " ";
                    input += "|";
                    input += " ";
                }

                lastClickTime = currentTime;
            }
        });

        return input;
    }

    public void setOutput(String output) {
        this.output = output;


    }

}



