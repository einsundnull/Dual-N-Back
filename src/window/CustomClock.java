package window;

import javax.swing.Timer;

/** Session clock — replaces JavaFX CustomClock from CustomWindow.jar. */
public class CustomClock {

    private static Timer clockTimer;
    private static int seconds = 0;
    private static boolean paused = false;

    public CustomClock() {}

    public static void startClock() {
        seconds = 0;
        if (clockTimer != null) clockTimer.stop();
        clockTimer = new Timer(1000, e -> {
            if (!paused) seconds++;
        });
        clockTimer.start();
    }

    public static void pauseTimer(boolean pause) {
        paused = pause;
    }

    public static void cancelTimer() {
        if (clockTimer != null) clockTimer.stop();
        clockTimer = null;
        seconds = 0;
    }

    public static String getTime() {
        int m = seconds / 60;
        int s = seconds % 60;
        return String.format("%02d:%02d", m, s);
    }
}
