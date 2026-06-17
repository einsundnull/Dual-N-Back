package window;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JPanel;

/** Swing recording button stub — records WAV via javax.sound.sampled. */
public class CustomRecordButton {

    private final JPanel  frame;
    private final JButton btn;
    private String id = "";
    private boolean recording = false;
    private TargetDataLine line;
    private String filePath;
    private Runnable onStart;

    public CustomRecordButton() {
        frame = new JPanel(null);
        btn   = new JButton("REC");
        btn.setBounds(0, 0, 60, 30);
        btn.setBackground(new Color(220, 80, 80));
        frame.add(btn);
        frame.setSize(60, 30);
        frame.setOpaque(false);

        btn.addActionListener(e -> toggleRecord());
    }

    private void toggleRecord() {
        if (!recording) startRecording(); else stopRecording();
    }

    private void startRecording() {
        try {
            AudioFormat fmt = new AudioFormat(44100, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, fmt);
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(fmt);
            line.start();
            recording = true;
            btn.setText("STOP");
            if (onStart != null) onStart.run();
            new Thread(() -> {
                try (AudioInputStream ais = new AudioInputStream(line)) {
                    AudioSystem.write(ais, AudioFileFormat.Type.WAVE,
                                      new File(filePath + ".wav"));
                } catch (IOException ex) { ex.printStackTrace(); }
            }).start();
        } catch (LineUnavailableException ex) { ex.printStackTrace(); }
    }

    private void stopRecording() {
        if (line != null) { line.stop(); line.close(); }
        recording = false;
        btn.setText("REC");
    }

    public void setID(String id)   { this.id = id; }
    public String getID()          { return id; }
    public void setLayoutX(double x) { frame.setBounds((int)x, frame.getY(), frame.getWidth(), frame.getHeight()); }
    public void setLayoutY(double y) { frame.setBounds(frame.getX(), (int)y, frame.getWidth(), frame.getHeight()); }

    public void setAudioFile(String path, String name, String addon, String fmt, Object type) {
        filePath = path + File.separator + name + addon;
    }

    public void setRunFunctions(Runnable onStart, Runnable onStop) { this.onStart = onStart; }

    public JPanel newButton() { return frame; }
}
