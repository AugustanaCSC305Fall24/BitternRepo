package edu.augustana.WebSocket;

import com.google.gson.*;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class UserPreferences {
    public static final File DEFAULT_USER_PREFERENCES_FILE = new File("user_preferences.json");

    private String primaryUserName = "User";
    private String primaryUserColorString = Color.RED.toString();
    private int botSpeed = 5;

    public UserPreferences() {
    }

    public String getPrimaryUserName() {
        return primaryUserName;
    }

    public void setPrimaryUserName(String primaryUserName) {
        this.primaryUserName = primaryUserName;
    }

    public Color getPrimaryUserColor() {
        return Color.valueOf(primaryUserColorString);
    }

    public void setPrimaryUserColor(Color primaryUserColor) {
        this.primaryUserColorString = primaryUserColor.toString();
    }

    public int getBotSpeed() {
        return botSpeed;
    }

    public void setBotSpeed(int botSpeed) {
        this.botSpeed = botSpeed;
    }

    public String toJSON() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public void saveToJSONFile(File preferencesFile) {
        try {
            PrintWriter out = new PrintWriter(preferencesFile);
            out.println(toJSON());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UserPreferences loadFromJSONFile(File preferencesFile) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(new FileReader(preferencesFile), UserPreferences.class);
        } catch (Exception e) {
            System.out.println("Preferences file " + preferencesFile + " not found or invalid. Loading default preferences instead.");
            return new UserPreferences();
        }
    }

}
