package edu.augustana;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class ServerController {

    @FXML private Button dashButton;
    @FXML private Button dotButton;
    @FXML private Button welcomeButton;
    @FXML private Button helpPageButton;

    @FXML
    void openHelpScreen(ActionEvent event) {
//        RadioApp.setRoot("HelpPageScreen");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(RadioApp.class.getResource("HelpPageScreen.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Help Page");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("Can't Load New Window");
        }
    }

    // Method to switch to the Welcome screen
    @FXML
    private void switchToWelcome(ActionEvent event) throws IOException {
        RadioApp.setRoot("WelcomeScreen");
    }

}
