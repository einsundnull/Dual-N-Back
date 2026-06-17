package training;

import java.util.Timer;
import java.util.TimerTask;

import window.CustomClock;

public class TimerClass {

    public static int     counter;
    public static boolean endSession;
    public static boolean paused    = true;
    public static boolean endTrial;
    public static boolean interrupt = true;
    public static int     passedTrials = 1;
    public static int     sessionLengthInTrials;
    public static int     speed     = 2;
    public static Timer   timer;
    public static int     repeatTime = 950;
    public static double  maxPresentations = 31;
    private static final int delay  = 2000;
    static int time = 0;
    private static Timer timerII;

    public static void setTimerI(Runnable runIt) {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (paused) {
                    timer.purge();
                    counter = 0;
                    CustomClock.pauseTimer(true);
                    return;
                }
                if (!interrupt) {
                    CustomClock.pauseTimer(false);
                    counter++;
                    if (counter >= speed) {
                        runIt.run();
                        timer.purge();
                        counter = 0;
                    }
                }
            }
        }, delay, repeatTime);
    }

    public static void setTimerIIForTestPurposes() {
        timerII = new Timer(true);
        time = 0;
        timerII.scheduleAtFixedRate(new TimerTask() {
            @Override public void run() { time++; }
        }, delay, 1000);
    }
}
