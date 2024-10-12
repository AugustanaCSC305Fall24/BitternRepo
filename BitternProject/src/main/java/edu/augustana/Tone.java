package edu.augustana;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Tone {

    public Tone() throws LineUnavailableException {
    }

    enum SoundType {DOT, DASH, REST};
    static final AudioFormat af =
            new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
    static SourceDataLine line;

    static {
        try {
            line = AudioSystem.getSourceDataLine(af);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

//    public static void main(String[] args) throws LineUnavailableException {
//        final AudioFormat af =
//                new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
//        SourceDataLine line = AudioSystem.getSourceDataLine(af);
//        line.open(af, Note.SAMPLE_RATE);
//        line.start();
//        for  (Note n : Note.values()) {
//            play(line, n, 500);
//            play(line, Note.REST, 10);
//        }
//        line.drain();
//        line.close();
//    }

    public static void play(SoundType soundType) throws LineUnavailableException {

        Note note = Note.E4;
        int ms;
//        line.open(af, Note.SAMPLE_RATE);
//        line.start();

        if (soundType.equals(SoundType.DASH)) {
            ms = 400;
        } else if (soundType.equals(SoundType.DOT)){
            ms = 100;
        } else {
            ms = 10;
            note = Note.REST;
        }

//        ms = Math.min(ms, Note.SECONDS * 1000);
        int length = Note.SAMPLE_RATE * ms / 2000;
        int count = line.write(note.data(), 0, length);
//        line.drain();
//        line.close();
    }
}

enum Note {

    REST, E4;// REST, A4, A4$, B4, C4, C4$, D4, D4$, E4, F4, F4$, G4, G4$, A5;
    public static final int SAMPLE_RATE = 16 * 1024; // ~16KHz
    public static final int SECONDS = 2;
    private byte[] sin = new byte[SECONDS * SAMPLE_RATE];

    Note() {
        int n = this.ordinal();
        if (n > 0) {
            double exp = ((double) n - 1) / 12d;
            double f = 440d * Math.pow(2d, exp);
            for (int i = 0; i < sin.length; i++) {
                double period = (double)SAMPLE_RATE / f;
                double angle = 2.0 * Math.PI * i / period;
                sin[i] = (byte)(Math.sin(angle) * 127f);
            }
        }
    }

    public byte[] data() {
        return sin;
    }
}
