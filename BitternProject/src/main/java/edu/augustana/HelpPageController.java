package edu.augustana;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpPageController implements Initializable {

    @FXML private Button exitButton;
    @FXML private ListView<String> letterListView;
    @FXML private ListView<String> phraseListView;
    private static String[] lettersAndCode = new String[37];
    private static String[] phrasesAndCodeWords = new String[13];

    //Make list to be displayed on the list view
    //Tried to make this the same method and makeListCode, however was having an issue while char[] to String[]
    private void makeListLetters(){
        int size = Math.min(Translator.englishLetters.length, Translator.morseCodeLetters.length);
        for (int i = 0; i < size; i++){
            lettersAndCode[i] = (Translator.englishLetters[i] + "    " + Translator.morseCodeLetters[i]);
        }
    }

    private void makeListCode(){
        int size = Math.min(Translator.codeWords.length, Translator.codeWordTranslation.length);
        for (int i = 0; i < size; i++){
            phrasesAndCodeWords[i] = (Translator.codeWords[i] + "    " + Translator.codeWordTranslation[i]);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        makeListLetters();
        makeListCode();
        letterListView.getItems().addAll(lettersAndCode);
        phraseListView.getItems().addAll(phrasesAndCodeWords);
    }
}
