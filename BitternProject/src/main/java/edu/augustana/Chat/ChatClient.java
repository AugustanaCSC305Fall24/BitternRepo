package edu.augustana.Chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import edu.augustana.Controllers.ScenarioController;
import edu.augustana.bots.ChatBot;
import org.json.JSONArray;
import org.json.JSONObject;


import javafx.scene.paint.Color;


public class ChatClient {

    private static List<ChatMessage> messages = new ArrayList<>();
    private static ChatBot currentBot;

    public ChatClient() {
        messages.add(new ChatMessage("Hello! Help me", "assistant", Color.BLACK));
    }

    public static void setCurrentBot(ChatBot bot) {
        currentBot = bot;
    }

    public static ChatBot getCurrentBot() {
        return currentBot;
    }

    public static void sendMessage(String messageContent) {
        try {
            if (currentBot == null) { //debugging
                throw new IllegalStateException("No bot selected. Please choose a bot.");
            }

            // Add user message to the list
            messages.add(new ChatMessage(messageContent, "user", Color.BLACK));

            // Prepare JSON payload
            JSONArray jsonMessages = new JSONArray();
            for (ChatMessage message : messages) {
                JSONObject jsonMessage = new JSONObject();
                jsonMessage.put("role", message.getSender());
                jsonMessage.put("content", message.getText());
                jsonMessage.put("type", "text");
                jsonMessages.put(jsonMessage);
            }

            JSONObject payload = new JSONObject();
            payload.put("query", jsonMessages);
            payload.put("frequency",currentBot.getPersonalityType());
            // Log the JSON payload
            System.out.println("JSON Payload: " + payload.toString());


            // Send HTTP POST request
            URL url = new URL("https://hamapi-abdulsz-abduls-projects-03968352.vercel.app/rag/");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = payload.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(responseCode == 200 ? conn.getInputStream() : conn.getErrorStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
            System.out.println(response.toString());

            // Read the response


                // Process the response
                JSONObject jsonResponse = new JSONObject(response.toString());
                String assistantMessage = jsonResponse.getString("response");

                messages.add(new ChatMessage(assistantMessage, currentBot.getName(), currentBot.getTextColor()));


                ChatMessage newMessage = new ChatMessage(assistantMessage, "assistant", Color.BLACK);

                ChatMessage.addMessage(newMessage);
            ScenarioController controller = new ScenarioController();
            controller.addMessageToChatLogUI(newMessage);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<ChatMessage> getMessages() {
        return messages;
    }
}

