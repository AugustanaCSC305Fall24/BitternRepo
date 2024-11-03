package edu.augustana;

import javax.sound.sampled.LineUnavailableException;

public abstract class Controller {

    RadioApp app = new RadioApp();

    public void setApp(RadioApp app) {this.app = app;}

    public void dit() throws LineUnavailableException {
    }

    public void dah() throws LineUnavailableException {
    }

    public void sendAction() throws LineUnavailableException {}


}
