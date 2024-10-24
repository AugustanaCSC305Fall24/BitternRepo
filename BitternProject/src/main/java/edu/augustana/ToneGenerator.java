package edu.augustana;

import javax.sound.sampled.*;

//  Couldn't find much on the internet for making sound w/o an audio file
//  so turned to ChatGPT to help write this class
public class ToneGenerator {

    public static void main(String[] args) throws LineUnavailableException {
        // Parameters for tone generation
        float sampleRate = 44100;   // Standard CD-quality sample rate
        double duration = 5;           // Duration in seconds
        double frequency = 440;     // Frequency in Hertz (A4 note)

        // Generate tone
        byte[] tone = generateSineWave(frequency, duration, sampleRate);

        // Play the tone
        playSound(tone, sampleRate);

        //Play dit and dah
        playDah(44100);
        playDit(44100);

    }

    // Method to generate a sine wave
    public static byte[] generateSineWave(double freq, double duration, float sampleRate) {
        int numSamples = (int)(duration * sampleRate);
        byte[] output = new byte[2 * numSamples];

        for (int i = 0; i < numSamples; i++) {
            double angle = 2.0 * Math.PI * i / (sampleRate / freq);
            short sample = (short)(Math.sin(angle) * Short.MAX_VALUE);

            // Convert sample to bytes (little-endian)
            output[2 * i] = (byte)(sample & 0xFF);         // LSB
            output[2 * i + 1] = (byte)((sample >> 8) & 0xFF);  // MSB
        }

        return output;
    }

    // Method to play the sound
    public static void playSound(byte[] sound, float sampleRate) throws LineUnavailableException {
        AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);

        // Open the line and start playback
        line.open(format);
        line.start();
        line.write(sound, 0, sound.length);

        // Finish and close
        line.drain();
        line.close();
    }

    public static void playDit(float sampleRate) throws LineUnavailableException {
        //float sampleRate = 44100;
        byte[] tone = generateSineWave(440, .06, sampleRate);
        playSound(tone, sampleRate);
    }

    public static void playDah(float sampleRate) throws LineUnavailableException {
        //float sampleRate = 44100;
        byte[] tone = generateSineWave(440, .18, sampleRate);
        playSound(tone, sampleRate);
    }

}