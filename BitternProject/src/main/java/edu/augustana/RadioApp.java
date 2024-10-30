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
    public void start(Stage stage) throws IOException, LineUnavailableException{
        scene = new Scene(loadFXML("WelcomeScreen"), 640, 480);
        stage.setScene(scene);
        stage.show();
        ScenarioController controller = new ScenarioController();
        scene.setOnKeyPressed(event -> {
            //System.out.println(event.getCode());
            try{
                switch(event.getCode()) {
                    case N:
                        controller.dit();
                        break;
                    case M:
                        controller.dah();
                        break;
                    case A:
                        controller.sendAction();
                        break;
                    default:
                        break;
                }
            } catch (LineUnavailableException e){
                throw new RuntimeException(e);
            }

        });

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