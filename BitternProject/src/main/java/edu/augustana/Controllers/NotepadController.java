package edu.augustana.Controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.augustana.Radio.RadioApp;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;

public class NotepadController {

    public static final File NOTEPAD = new File("notepad.json");

    @FXML private static TextArea notepadContent = new TextArea();
    private String note = notepadContent.getText();

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

    public void saveToFile(File file) throws IOException {
        System.out.println(getContent());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String notePadText = gson.toJson(notepadContent.getText());
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println(notePadText);
        writer.close();
//        try {
//            PrintWriter out = new PrintWriter(file);
//            out.println(toJSON());
//            out.close();
//        } catch (Exception e) {
//            System.out.println("Error saving preferences to file " + file + ": " + e.getMessage());
//            e.printStackTrace();
//        }
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
    private void menuFileSave() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Notepad");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        Window mainWindow = RadioApp.getScene().getWindow();
        File choosenFile = fileChooser.showSaveDialog(mainWindow);
        saveToFile(choosenFile);
    }

}
