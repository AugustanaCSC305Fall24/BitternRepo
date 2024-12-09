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

//    public static final File NOTEPAD = new File("notepad.json");

    @FXML private TextArea notepadContent;

    @FXML
    public void initialize() {

    }

    // Serialize only the content of the TextArea
    private static class NotepadData {
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

    public void saveToFile(File file) {
        try {
            PrintWriter out = new PrintWriter(file);
            out.println(toJSON());
            out.close();
        } catch (Exception e) {
            System.out.println("Error saving to file " + file + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static NotepadData loadFromJSONFile(File file) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(new FileReader(file), NotepadData.class);
        } catch (Exception e) {
            System.out.println(" file " + file + " not found or invalid. Setting to empty instead.");
            return new NotepadData("");
        }
    }




    @FXML
    private void menuFileOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Notepad");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
//        Window mainWindow = RadioApp.getScene().getWindow();
        File choosenFile = fileChooser.showOpenDialog(notepadContent.getScene().getWindow());

        NotepadData notepadData = loadFromJSONFile(choosenFile);
        notepadContent.setText(notepadData.content);
    }

    @FXML
    private void menuFileSave() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Notepad");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
//        Window mainWindow = RadioApp.getScene().getWindow();
        File choosenFile = fileChooser.showSaveDialog(notepadContent.getScene().getWindow());
        saveToFile(choosenFile);
    }

}
