package logics;

import static lists.Lists.progressBars;
import static lists.Lists.progressIndexModes;

import java.util.ArrayList;

import screens.StartScreen;
import screens.TrainingScreen;
import training.TimerClass;
import window.SwingPanel;

public class DurationTraining {

    public static int    distanceX = 1;
    public static double paneHeight = 1;
    public static double opacity    = 0.3;
    public static String paneColor  = "blanchedalmond";
    public static boolean showProgressBarDurationTraining = true;
    public static boolean showProgressBarRegularTraining  = false;
    public static boolean showRightAndFalseClicksInProgressBar = false;
    public static boolean showRightClicksImediately = false;

    private static int convertTimeToPresentations() {
        return (int)(StartScreen.sessionLengthInMinutes * 15.5);
    }

    public static int addaptMaxPresentationsToRepeatTime() {
        if (StartScreen.durationTraining) {
            TimerClass.maxPresentations = convertTimeToPresentations();
        } else {
            TimerClass.maxPresentations = 31;
        }
        double max = TimerClass.maxPresentations;
        if (TimerClass.repeatTime < 1000) {
            double eins = TimerClass.repeatTime;
            double time = 1 - eins / 1000;
            max = max + (max * time);
        }
        if (StartScreen.durationTraining) {
            TimerClass.sessionLengthInTrials = 0;
        } else {
            TimerClass.sessionLengthInTrials = StartScreen.sessionLengthInMinutes / 2;
        }
        return (int) max;
    }

    public static ArrayList<ArrayList<SwingPanel>> colorMatches(
            boolean[] clickedRight, int[] index,
            ArrayList<ArrayList<SwingPanel>> list) {

        for (int n = 0; n < Results.includedModes; n++) {
            try {
                String id = clickedRight[n] ? "1" : "-1";
                SwingPanel bar = list.get(n).get(index[n]);
                bar.setId(id);
                if (!showRightClicksImediately) {
                    bar.setOpacity(opacity * 0.1);  // simplified fade-out
                } else {
                    bar.setOpacity(0);
                }
            } catch (Exception ignored) {}

            try {
                if (showRightAndFalseClicksInProgressBar && index[n] > 0) {
                    SwingPanel prev = list.get(n).get(index[n] - 1);
                    prev.setStyle("-fx-background-color:" + (clickedRight[n] ? "green" : "red"));
                    if (!showRightClicksImediately) {
                        prev.setOpacity(opacity);
                    }
                }
            } catch (Exception ignored) {}

            progressIndexModes[n]++;
        }
        return list;
    }

    public static ArrayList<ArrayList<String>> storeDurationInput(
            boolean[] match, ArrayList<ArrayList<String>> list) {
        for (int n = 0; n < Results.includedModes; n++) {
            try {
                list.get(n).add(match[n] ? "1" : "0");
            } catch (Exception ignored) {}
        }
        return list;
    }

    public static void colorRightClicks() {
        for (int n = 0; n < Results.includedModes; n++) {
            for (SwingPanel bar : progressBars.get(n)) {
                String color = "1".equals(bar.getId()) ? "green" : "red";
                bar.setStyle("-fx-background-color:" + color);
            }
        }
    }

    public static void resetProgressBar() {
        for (int n = 0; n < Results.includedModes; n++) {
            for (SwingPanel bar : progressBars.get(n)) {
                bar.setStyle("-fx-background-color:" + paneColor);
                bar.setId("0");
            }
        }
    }

    public static void resetProgressBarSize(boolean interrupt) {
        double h  = interrupt ? 6 : 1;
        double op = interrupt ? 1 : opacity;
        for (int n = 0; n < Results.includedModes; n++) {
            double y = TrainingScreen.height - h - (h * Results.includedModes * n) - 2;
            for (SwingPanel bar : progressBars.get(n)) {
                int bx = bar.getBounds().x;
                int bw = bar.getBounds().width;
                bar.setBounds(bx, (int) y, bw, (int) h);
                bar.setOpacity(op);
            }
        }
    }

    private static void setProgressBarVisible(boolean value) {
        for (int n = 0; n < Results.includedModes; n++) {
            for (SwingPanel bar : progressBars.get(n)) bar.setVisible(value);
        }
    }

    public static void setProgressBarVisible() {
        setProgressBarVisible(StartScreen.durationTraining
            ? showProgressBarDurationTraining
            : showProgressBarRegularTraining);
    }
}
