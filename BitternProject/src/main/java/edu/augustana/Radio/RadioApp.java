package edu.augustana.Radio;

import edu.augustana.Controllers.WelcomeController;
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
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/augustana/Controllers/WelcomeScreen.fxml"));
        Parent root = loader.load();
        scene = new Scene(root, 900, 640); //640x640 is the default size
        stage.setScene(scene);
        stage.show();

        // Assuming WelcomeController is the controller for WelcomeScreen.fxml
        WelcomeController welcomeController = loader.getController();
        welcomeController.setApp(this);
    }

    public static Scene getScene() {
        return scene;
    }

    public void switchToWelcome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/augustana/Controllers/WelcomeScreen.fxml"));
        Parent root = loader.load();
        scene.setRoot(root);
        WelcomeController welcomeController = loader.getController();
        welcomeController.setApp(this);
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RadioApp.class.getResource("/edu/augustana/Controllers/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void createNewWindow(String fxml, String title) {
        try {
            Parent root1 = loadFXML(fxml);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("Can't Load New Window: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
