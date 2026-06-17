package window;

import java.io.File;
import java.net.MalformedURLException;

import javax.swing.JButton;
import javax.swing.JPanel;

import training.AudioClass;

/** Swing audio-play button stub. */
public class CustomPlayAudioButton {

    private final JPanel  frame;
    private final JButton btn;
    private String id = "";
    private String filePath;
    private Runnable onPlay;

    public CustomPlayAudioButton() {
        frame = new JPanel(null);
        btn   = new JButton("▶");
        btn.setBounds(0, 0, 60, 30);
        frame.add(btn);
        frame.setSize(60, 30);
        frame.setOpaque(false);

        btn.addActionListener(e -> {
            if (onPlay != null) onPlay.run();
            if (filePath != null) {
                try { AudioClass.playAsync(new File(filePath + ".wav").toURI().toURL()); }
                catch (MalformedURLException ex) { ex.printStackTrace(); }
            }
        });
    }

    public void setID(String id)   { this.id = id; }
    public String getID()          { return id; }
    public void setLayoutX(double x) { frame.setBounds((int)x, frame.getY(), frame.getWidth(), frame.getHeight()); }
    public void setLayoutY(double y) { frame.setBounds(frame.getX(), (int)y, frame.getWidth(), frame.getHeight()); }

    public void setAudioFile(String path, String name, String addon, String fmt) {
        filePath = path + File.separator + name + addon;
    }

    public void setRunFunctions(Runnable r) { this.onPlay = r; }

    public JPanel newButton() { return frame; }
}
