# BitternRepo
Team Bittern's Educational HAM Radio Simulator
  # Bittern Project

## Overview

The Bittern Project is a Java-based application that simulates the HAM radio communication system. 
It includes features such as chat messaging, Morse code translation, white noise generation, and bot interactions.

## Features

- **Chat Messaging**: Users can send and receive messages.
- **Morse Code Translation**: Messages can be translated to and from Morse code.
- **White Noise Generation**: Simulates radio static noise.
- **Bot Interactions**: Bots can send and receive messages, including in Morse code.
- **Scenario Management**: Save and load chat scenarios to/from JSON files.

## Project Structure

- `edu.augustana.Controllers`: Contains the controllers for the application.
  - `ScenarioController.java`: Manages the main scenario interactions.
  - `ServerController.java`: Manages server-related functionalities.
- `edu.augustana.Chat`: Contains classes related to chat functionalities.
  - `ChatClient.java`: Handles sending messages to bots.
  - `ChatMessage.java`: Represents a chat message.
  - `ChatRoom.java`: Manages the chat room and its messages.
  - `ChatBot.java`: Represents a chat bot.
- `edu.augustana.Input`: Contains classes related to input processing.
  - `Translator.java`: Handles translation between text and Morse code.
- `edu.augustana.Radio`: Contains classes related to radio functionalities.
  - `RadioApp.java`: Main application class.
  - `ToneGenerator.java`: Generates tones for Morse code.
  - `WhiteNoise.java`: Generates white noise.

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven
## Final Presentation
 Google slides link : https://docs.google.com/presentation/d/1FJL3WPiNhpQ3h9C3HcuYPaTmGjRBMy-XDj1W3bf5oy0/edit?usp=sharing
### Installation
 Clone the repository:
   ```sh
   git clone https://github.com/AugustanaCSC305Fall24/BitternRepo.git


