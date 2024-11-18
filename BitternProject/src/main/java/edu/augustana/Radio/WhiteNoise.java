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
    public static int volume = -10; // Volume ranges from -80 to 6

    SourceDataLine line;
    public boolean exitExecution = false;

    public static void main(String[] args) {
        try {
            generatorThread = new WhiteNoise();
            generatorThread.start();
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

            line = (SourceDataLine)AudioSystem.getLine(info);
            line.open(format);
            line.start();



        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        FloatControl volumeControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(volume);

        ByteBuffer buffer = ByteBuffer.allocate(PACKET_SIZE);

        Random random = new Random();
        while (exitExecution == false) {
            buffer.clear();
            for (int i=0; i < PACKET_SIZE /SAMPLE_SIZE; i++) {
                buffer.putShort((short) (random.nextGaussian() * Short.MAX_VALUE));
            }
            volumeControl.setValue(volume);
            line.write(buffer.array(), 0, buffer.position());
        }



//        System.out.println(volume.getMaximum());
//        System.out.println(volume.getMinimum());

        line.drain();
        line.close();
    }

    public void exit() {
        exitExecution =true;
    }

    public static void setVolume(int volumeSet) {
        volume = volumeSet;
    }




}