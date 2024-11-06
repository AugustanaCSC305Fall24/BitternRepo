package edu.augustana;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class NotepadController implements Initializable {

    @FXML
    private TextArea notesText;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        notesText.setWrapText(true);
    }

    public void setNotesText(String text) {
        notesText.setText(text);
    }
}
