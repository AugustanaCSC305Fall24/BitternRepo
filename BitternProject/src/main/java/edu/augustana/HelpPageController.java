package edu.augustana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpPageController implements Initializable {

    @FXML private Button exitButton;
    @FXML private ListView<String> letterListView;
    @FXML private ListView<String> phraseListView;

    public static String[] lettersAndCode
            = { "a   .-",   "b   -...", "c   -.-.", "d   -..",  "e   .",
            "f   ..-.", "g   --.",  "h   ....", "i   ..",   "j   .---",
            "k   -.-",  "l   .-..", "m   --",   "n   -.",   "o   ---",
            "p   .--.", "q   --.-", "r   .-.",  "s   ...",  "t   -",
            "u   ..-",  "v   ...-", "w   .--",  "x   -..-", "y   -.--",
            "z   --..", "    |", "0   -----", "1   .----", "2   ..---",
            "3   ...--", "4   ....-", "5   .....", "6   -....",
            "7   --...", "8   ---..", "9   ----."};

    public static String[] phrasesAndCodeWords
            = { "CQ - Calling all stations", "GM - Good morning", "GA - Good afternoon", "GE - Good evening", "GN - Good night",
            "AGN - Again", "QSB - Fading signal", "QRS - Send slower", "QRQ - Send faster", "WPM - Words per minute",
            "R - Roger", "TU - Thank you", "73 - Best wishes"};

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        letterListView.getItems().addAll(lettersAndCode);
        phraseListView.getItems().addAll(phrasesAndCodeWords);
    }


}
