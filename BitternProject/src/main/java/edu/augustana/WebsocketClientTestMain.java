/*
  WebSocket Chat Client Example, adapted from the client code in: https://www.php.cn/faq/585542.html
  (Modified by Dr. Stonedahl to use JSON/GSON and the Chatterbox ChatMessage class)
 */
package edu.augustana;

import com.google.gson.Gson;
import jakarta.websocket.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URI;

@ClientEndpoint
public class WebsocketClientTestMain extends Application {

    private final String USER_NAME = "Penguin";;

    private Session session;
    private TextArea messageArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Real Time Application");

        VBox vbox = new VBox();

        messageArea = new TextArea();
        messageArea.setEditable(false);

        TextField inputField = new TextField();
        inputField.setOnAction(event -> {
            String text = inputField.getText();
            sendMessage(text);
            inputField.setText("");
        });

        vbox.getChildren().addAll(messageArea, inputField);

        primaryStage.setScene(new Scene(vbox, 400, 300));
        primaryStage.show();

        connect();
    }

    @Override
    public void stop() {
        try {
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String text) {
        ChatMessage chatMessage = new ChatMessage(text, USER_NAME, Color.RED);
        System.out.println(chatMessage.getSender() + chatMessage.getText());
        messageArea.appendText(chatMessage.getSender() + "(YOU) : " + chatMessage.getText()  + "\n");
        Gson gson = new Gson();
        String jsonText = gson.toJson(chatMessage);
        System.out.println("Sending WebSocket message: " + jsonText);
        session.getAsyncRemote().sendText(jsonText);
    }
    @OnMessage
    public void onMessage(String jsonMessage) {
        System.out.println("Received WebSocket message: " + jsonMessage);
        ChatMessage chatMessage = new Gson().fromJson(jsonMessage, ChatMessage.class);
        messageArea.appendText(chatMessage.getSender() + " : " + chatMessage.getText()  + "\n");
    }

    private void connect() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(this, new URI("ws://localhost:8000/ws/penguin"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}