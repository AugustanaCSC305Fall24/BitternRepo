package edu.augustana.Radio;

import javax.sound.sampled.*;
import java.nio.ByteBuffer;
import java.util.Random;

// Code from GitHub
// https://gist.github.com/m5mat/f622df23a49586337009c60a9966964c

public class WhiteNoise extends Thread {
    private static WhiteNoise generatorThread;

    final static public int SAMPLE_SIZE = 2;
    final static public int PACKET_SIZE = 5000;
    public static int volume = -30; // Volume ranges from -80 to 6 default is -10

    static SourceDataLine line;
    public static boolean exitExecution = false;

    // Testing the WhiteNoise
    public static void main(String[] args) {
        try {
            generatorThread = new WhiteNoise();
            generatorThread.start();
            generatorThread.setVolume(50);
            Thread.sleep(10000);
            generatorThread.exit();


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void play() {
        try {
            AudioFormat format = new AudioFormat(44100, 16, 1, true, true);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format, PACKET_SIZE * 2);

            if (!AudioSystem.isLineSupported(info)) {
                throw new LineUnavailableException();
            }

            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            FloatControl volumeControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);

            ByteBuffer buffer = ByteBuffer.allocate(PACKET_SIZE);
            Random random = new Random();

            while (!exitExecution) {
                buffer.clear();
                for (int i = 0; i < PACKET_SIZE / SAMPLE_SIZE; i++) {
                    // Scale sample amplitude by volume
                    short sample = (short) (random.nextGaussian() * Short.MAX_VALUE * Math.pow(10.0, volume / 20.0));
                    buffer.putShort(sample);
                }

                line.write(buffer.array(), 0, buffer.position());
            }

            line.drain();
            line.close();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    public void exit() {
        exitExecution =true;
    }

    public static void setVolume(int volumeSet) {
       volume = volumeSet;
        System.out.println("White Noise Volume Set to: " + volumeSet + " dB");
    }


    public static void stopPlaying() {
        line.stop();
    }


    public static void reset() {
        stopPlaying();
        volume = -30; //defalut volume
        exitExecution = false;
        System.out.println("White Noise Reset to Default Settings");
    }

}