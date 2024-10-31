package edu.augustana;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

/** JavaFX App **/

public class RadioApp extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException, LineUnavailableException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeScreen.fxml"));
        Parent root = loader.load();
        scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();

        // Assuming WelcomeController is the controller for WelcomeScreen.fxml
        WelcomeController welcomeController = loader.getController();
        welcomeController.setApp(this);
    }

    public void switchToScenario() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenarioScreen.fxml"));
        Parent root = loader.load();

        scene.setRoot(root);

        ScenarioController scenarioController = loader.getController();
        scenarioController.setApp(this); // Set the app field

        scene.setOnKeyPressed(event -> {
            try {
                switch (event.getCode()) {
                    case N:
                    case A: // Bind 'A' key to dit
                        scenarioController.dit();
                        break;
                    case M:
                    case S: // Bind 'S' key to dah
                        scenarioController.dah();
                        break;
                    case ENTER:
                        scenarioController.sendAction();
                        break;
                    default:
                        break;
                }
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void switchToWelcome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeScreen.fxml"));
        Parent root = loader.load();
        scene.setRoot(root);

        WelcomeController welcomeController = loader.getController();
        welcomeController.setApp(this);
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RadioApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static void main(String[] args) {
        launch();
    }

    public static void createNewWindow(String fxml, String title){
        try {
            Parent root1 = loadFXML((String)fxml);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("Can't Load New Window");
        }
    }
}