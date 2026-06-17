package training;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import language.LanguageClass;

/**
 * Audio playback without JavaFX MediaPlayer.
 *
 * NOTE: javax.sound.sampled supports WAV, AIFF and AU natively.
 *       The original audio files are MP3. Convert them to WAV, or add
 *       the MP3SPI library (mp3spi-1.9.5.jar + tritonus-share.jar)
 *       to the classpath — then AudioSystem.getAudioInputStream() will
 *       accept MP3 URLs transparently without changing this code.
 */
public class AudioClass {

    public static ArrayList<URL> audio = new ArrayList<>();

    /** Play a numbered audio file for the given language asynchronously. */
    public static void playSound(int tempIndexLang, int indexAud) {
        String lang = LanguageClass.word(tempIndexLang, 28);
        String path = "/audio/" + lang + "_" + indexAud + ".wav";
        URL url = AudioClass.class.getResource(path);
        if (url == null) {
            // fallback: try without underscore separator
            path = "/audio/" + lang + indexAud + ".wav";
            url = AudioClass.class.getResource(path);
        }
        if (url == null) return;
        playAsync(url);
    }

    /** Play any audio URL asynchronously; returns immediately. */
    public static void playAsync(URL url) {
        new Thread(() -> {
            try {
                AudioInputStream raw = AudioSystem.getAudioInputStream(url);
                AudioFormat fmt = raw.getFormat();

                // Convert to PCM if needed (required for MP3 via MP3SPI)
                AudioFormat pcm = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    fmt.getSampleRate(), 16,
                    fmt.getChannels(), fmt.getChannels() * 2,
                    fmt.getSampleRate(), false);
                AudioInputStream pcmStream = AudioSystem.getAudioInputStream(pcm, raw);

                Clip clip = AudioSystem.getClip();
                clip.open(pcmStream);
                clip.start();
                clip.addLineListener(e -> {
                    if (e.getType() == LineEvent.Type.STOP) clip.close();
                });
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }, "audio-player").start();
    }

    /** Play a custom file path asynchronously. */
    public static void playFile(String filePath) {
        try {
            playAsync(new java.io.File(filePath).toURI().toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
