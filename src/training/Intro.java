package training;

import java.awt.Image;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import screens.StartScreen;

public class Intro {

    static Timer timer;
    private int layoutY;
    private int layoutX;
    private boolean moveDown = true;
    private JLabel imgLabel;
    private static boolean cancel;

    public Intro() {
        cancel = false;

        if (timer == null) {
            layoutX = (int)(StartScreen.screenWidth  * 0.92);
            layoutY = (int)(StartScreen.screenHeight * 0.02);

            URL url = getClass().getResource("/img/Intro.png");
            if (url == null) return;

            ImageIcon icon = new ImageIcon(
                new ImageIcon(url).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            imgLabel = new JLabel(icon);
            imgLabel.setBounds(layoutX, layoutY, 30, 30);
            StartScreen.mainWindow.add(imgLabel);

            timer = new Timer(true);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override public void run() {
                    if (cancel) {
                        timer.cancel();
                        timer = null;
                        SwingUtilities.invokeLater(() -> imgLabel.setVisible(false));
                        return;
                    }
                    SwingUtilities.invokeLater(() -> {
                        imgLabel.setLocation(layoutX, layoutY);
                        if (moveDown) layoutY++; else layoutY--;
                        if (layoutY < StartScreen.screenHeight * 0.022) moveDown = true;
                        if (layoutY > StartScreen.screenHeight * 0.225) moveDown = false;
                    });
                }
            }, 100, 10);
        }
    }

    public static void cancel() {
        cancel = true;
    }
}
