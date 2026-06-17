package window;

import javax.swing.Timer;

/** One-shot delayed execution — replaces JavaFX CustomTimer from CustomWindow.jar. */
public class CustomTimer {

    public CustomTimer(int delayMs, Runnable action) {
        Timer t = new Timer(delayMs, e -> action.run());
        t.setRepeats(false);
        t.start();
    }
}
