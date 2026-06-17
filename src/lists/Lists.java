package lists;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import logics.DurationTraining;
import logics.Results;
import screens.TrainingScreen;
import training.Rect;
import training.TimerClass;
import window.SwingLabel;
import window.SwingPanel;

public class Lists {

    public static ArrayList<ArrayList<SwingPanel>> progressBars = new ArrayList<>();
    public static ArrayList<ArrayList<String>>     clicksList   = new ArrayList<>();
    public static ArrayList<SwingLabel>            rect         = new ArrayList<>();

    public static int[][]    indexes;
    public static int[]      progressIndexModes;
    public static int[]      randomIndex;
    public static double[]   percentageList;
    public static double[]   rightValueMode;
    public static boolean[]  clickedRightMode;
    public static boolean[]  matchesMode;
    public static boolean[]  clickedToConfirmMatch;
    public static String[]   confirmMatchKeys;
    public static int[]      confirmMatchMouseButtons;   // replaces MouseButton[]
    public static String[]   colors;

    public static void setColorList() {
        colors = new String[] {
            "white", "yellow", "chocolate", "green",
            "rgb(136,163,19)", "rgb(3,211,252)",
            "blue", "red", "purple", "grey", "darkgrey"
        };
    }

    /** Swing-Timer-based fade replaced FadeTransition — no-op list needed for API compat. */
    public static void createTransitionList() {
        // Fade animations are handled inline via javax.swing.Timer in DurationTraining.
    }

    public static ArrayList<ArrayList<SwingPanel>> createProgressBar(
            double layoutY, ArrayList<ArrayList<SwingPanel>> list) {

        list.clear();
        int size = (int) (TimerClass.maxPresentations + Rect.nback - 1);

        for (int n = 0; n < Results.includedModes; n++) {
            list.add(new ArrayList<>());
            double y   = layoutY - DurationTraining.paneHeight - (Results.includedModes * n);
            double width = ((TrainingScreen.width - 2) /
                           (TimerClass.maxPresentations - Rect.nback)) - DurationTraining.distanceX;

            for (int i = 0; i < size; i++) {
                SwingPanel pane = new SwingPanel();
                pane.setOpaque(true);
                pane.setBounds(
                    (int)(2 + width * i + DurationTraining.distanceX * i),
                    (int) y,
                    (int) width,
                    (int) DurationTraining.paneHeight);
                pane.setStyle("-fx-background-color:" + DurationTraining.paneColor);
                pane.setId("0");
                list.get(n).add(pane);
            }
            // Last bar marks end of session
            int last = (int)(TimerClass.maxPresentations + Rect.nback - 2);
            list.get(n).get(last).setStyle("-fx-background-color: blue");
        }
        return list;
    }

    public static ArrayList<ArrayList<String>> createClickList(
            ArrayList<ArrayList<String>> clickList) {
        clickList.clear();
        for (int n = 0; n < Results.includedModes; n++) {
            clickList.add(new ArrayList<>());
        }
        return clickList;
    }

    public static int[][] createIndexes(int[][] indexes) {
        int size = Rect.nback * 5;
        indexes = new int[Results.includedModes][size];
        for (int n = 0; n < Results.includedModes; n++) {
            for (int i = 0; i < size; i++) {
                indexes[n][i] = -1;
            }
        }
        return indexes;
    }

    // Mouse button constants (replaces javafx.scene.input.MouseButton)
    public static final int MOUSE_PRIMARY   = MouseEvent.BUTTON1;
    public static final int MOUSE_MIDDLE    = MouseEvent.BUTTON2;
    public static final int MOUSE_SECONDARY = MouseEvent.BUTTON3;
}
