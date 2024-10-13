package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;

public class VolumeController {
//    @FXML
//    private Slider volumeSlider; // Link the FXML slider here

    // Called after FXML fields are initialized
    @FXML
    public void initialize(Slider volumeSlider) {
        // Set an initial volume value using the slider's value
        double initialVolume = volumeSlider.getValue();
        System.out.println("Initial slider value: " + initialVolume);

        // Add a listener to track changes in the slider value
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Slider value changed: " + newValue);
            // Use the value for your specific purpose, e.g., adjust volume or update UI
            handleSliderValue(newValue.doubleValue());
        });
    }

    // Method to handle slider value changes
    public void handleSliderValue(double value) {
        // This is where you can implement actions based on the slider value
        System.out.println("Handling slider value: " + value);
        // For example, adjust volume or modify another property in your application
    }
}
