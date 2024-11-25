package edu.augustana.Controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class NotepadController {

    public static final File NOTEPAD = new File("notepad.json");

    @FXML private static TextArea notepadContent = new TextArea();

    @FXML
    public void initialize() {
        if (notepadContent == null) {
            System.out.println("TextArea notepadContent is not initialized!");
        } else {
            System.out.println("TextArea notepadContent is properly initialized!");
        }
    }


    // Serialize only the content of the TextArea
    private class NotepadData {
        String content;

        NotepadData(String content) {
            this.content = content;
        }
    }

    public String getContent() {
        return notepadContent.getText();
    }

    public String toJSON() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        NotepadData data = new NotepadData(notepadContent.getText());
        return gson.toJson(data);
    }

    public void saveToJSONFile(File file) {
        try {
            PrintWriter out = new PrintWriter(file);
            out.println(toJSON());
            out.close();
        } catch (Exception e) {
            System.out.println("Error saving preferences to file " + file + ": " + e.getMessage());
//            e.printStackTrace();
        }
    }

    public static NotepadController loadFromJSONFile(File file) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(new FileReader(file), NotepadController.class);
        } catch (Exception e) {
            System.out.println("Preferences file " + file + " not found or invalid. Loading default preferences instead.");
            return new NotepadController();
        }
    }


    @FXML
    private void menuFileOpen() {
        NotepadController notepadString = loadFromJSONFile(NOTEPAD);
        notepadContent.setText(String.valueOf(notepadString));
    }

    @FXML
    private void menuFileSave() {
        saveToJSONFile(NOTEPAD);
    }

}
