package logics;

import static lists.Lists.clickedRightMode;
import static lists.Lists.clickedToConfirmMatch;
import static lists.Lists.confirmMatchKeys;
import static lists.Lists.confirmMatchMouseButtons;
import static lists.Lists.matchesMode;
import static lists.Lists.percentageList;
import static lists.Lists.progressIndexModes;
import static lists.Lists.randomIndex;
import static lists.Lists.rightValueMode;

import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.SwingUtilities;

import enums.Modes;
import files.ResultsFiles;
import language.LanguageClass;
import screens.StartScreen;
import screens.TrainingScreen;
import training.Rect;

public class Results {

    public static double presentations;
    public static double percentageAll;
    private static double decreaseThreshold = 0.75;
    private static double increaseThreshold = 0.85;
    public static double overallPercentage;
    public static int    includedModes;
    public static Modes  mode;
    private static final String manualText = "Press  ";

    public static boolean[] registerMatches(int[] randomIndex, int[][] indexes, boolean[] match) {
        for (int n = 0; n < includedModes; n++) {
            match[n] = (indexes[n][Rect.nback] != -1 && indexes[n][Rect.nback] == randomIndex[n]);
        }
        return match;
    }

    public static boolean[] registerClicks(boolean[] match, boolean[] clicked, boolean[] right) {
        for (int n = 0; n < includedModes; n++) {
            right[n] = (match[n] == clicked[n]);  // true if both match OR both don't
            if (!match[n] && clicked[n]) right[n] = false;  // false alarm
            if (match[n]  && !clicked[n]) right[n] = false; // miss
        }
        return right;
    }

    public static double[] increaseRightValue(boolean[] right, double[] counter) {
        for (int n = 0; n < includedModes; n++) {
            if (right[n]) counter[n]++; else counter[n]--;
        }
        return counter;
    }

    public static int[] createRandomIndex(int[] randomIndex) {
        for (int n = 0; n < includedModes; n++) {
            int temp = new Random().nextInt(Rect.randomIndexLimit);
            if (Rect.excludeFourFromRandomIndex) {
                while (temp == 4 || randomIndex[n] == -1) {
                    temp = new Random().nextInt(Rect.randomIndexLimit);
                }
            }
            randomIndex[n] = temp;
        }
        return randomIndex;
    }

    public static int[] increaseNumberOfMatches(int[][] indexes, int[] randomIndex, int factor) {
        for (int n = 0; n < includedModes; n++) {
            int r = new Random().nextInt(factor);
            if (r == 0) {
                randomIndex[n] = indexes[n][Rect.nback + 1];
                if (Rect.excludeFourFromRandomIndex) {
                    while (r == 4 || randomIndex[n] < 0) {
                        r = new Random().nextInt(Rect.randomIndexLimit);
                        randomIndex[n] = r;
                    }
                }
            }
        }
        return randomIndex;
    }

    public static int[][] storePresentedIndex(int[][] indexes, int[] randomIndex, int factor) {
        for (int n = 0; n < includedModes; n++) {
            int add = 2;
            for (int i = 0; i <= Rect.nback * add; i++) {
                randomIndex = increaseNumberOfMatches(indexes, randomIndex, factor);
                int temp = indexes[n][i + 1];
                indexes[n][i] = temp;
            }
            indexes[n][Rect.nback * add] = randomIndex[n];
        }
        return indexes;
    }

    public static double calculateTrialResults(double right) {
        if (right < 0) right = 0;
        double pct = right / (presentations - Rect.nback);
        return Math.min(1, Math.max(0, pct));
    }

    public static void inOrDecreaseNBackLevelByResultsDualTraining(double[] pctList) {
        int increase = 1;
        for (int n = 0; n < includedModes; n++) {
            if (pctList[n] < increaseThreshold && pctList[n] >= decreaseThreshold) increase = 0;
            if (pctList[n] < decreaseThreshold) { increase = -1; break; }
        }
        Rect.nback = Math.max(1, Rect.nback + increase);
    }

    public static void executeTrialResults() {
        double sum = 0;
        for (int n = 0; n < includedModes; n++) {
            percentageList[n] = calculateTrialResults(rightValueMode[n]);
            sum += percentageList[n];
        }
        overallPercentage = sum / includedModes;
        TrainingScreen.combinedFileWriteLogic();
        inOrDecreaseNBackLevelByResultsDualTraining(percentageList);

        SwingUtilities.invokeLater(() -> {
            TrainingScreen.lbl_n_back_Info_Pause.setText(
                Rect.nback + LanguageClass.word(StartScreen.indexLang, 25));
            TrainingScreen.lbl_n_Back.setText(Rect.nback + " - back");
        });
        Rect.indexCreateCounter = 0;
    }

    public static void countIncludedModes() {
        includedModes = 0;
        if (StartScreen.includeAudio)    includedModes++;
        if (StartScreen.includeColor)    includedModes++;
        if (StartScreen.includePosition) includedModes++;

        progressIndexModes      = new int[includedModes];
        matchesMode             = new boolean[includedModes];
        randomIndex             = new int[includedModes];
        clickedToConfirmMatch   = new boolean[includedModes];
        rightValueMode          = new double[includedModes];
        percentageList          = new double[includedModes];
        clickedRightMode        = new boolean[includedModes];
        confirmMatchMouseButtons = new int[includedModes];
    }

    public static void determineMode() {
        if (StartScreen.includeAudio && StartScreen.includeColor && StartScreen.includePosition) {
            mode = Modes.AUDIO_COLOR_POSITION;
            ResultsFiles.storingPathDuration = ResultsFiles.tempCombinedAudioColorPositionDuration;
            ResultsFiles.storingPathRegular  = ResultsFiles.tempCombinedAudioColorPositionRegular;
            Rect.indexPlayAudio = 0; Rect.indexShowColor = 1; Rect.indexShowPosition = 2;
            confirmMatchKeys         = new String[]{ TrainingScreen.confirmMatchAudio, TrainingScreen.confirmMatchColor, TrainingScreen.confirmMatchPosition };
            confirmMatchMouseButtons = new int[]{ MouseEvent.BUTTON3, MouseEvent.BUTTON1, MouseEvent.BUTTON2 };
            TrainingScreen.manualText = manualText + confirmMatchKeys[0] + "  For Audio Match\n"
                + manualText + confirmMatchKeys[1] + "  For Color Match\n"
                + manualText + confirmMatchKeys[2] + "  For Position Match";
        }
        if (StartScreen.includeAudio && !StartScreen.includeColor && !StartScreen.includePosition) {
            mode = Modes.AUDIO;
            ResultsFiles.storingPathDuration = ResultsFiles.tempCombinedAudioDuration;
            ResultsFiles.storingPathRegular  = ResultsFiles.tempCombinedAudioRegular;
            Rect.indexPlayAudio = 0;
            confirmMatchKeys         = new String[]{ TrainingScreen.confirmMatchAudio };
            confirmMatchMouseButtons = new int[]{ MouseEvent.BUTTON3 };
            TrainingScreen.manualText = manualText + confirmMatchKeys[0] + "  For Audio Match";
        }
        if (!StartScreen.includeAudio && StartScreen.includeColor && !StartScreen.includePosition) {
            mode = Modes.COLOR;
            ResultsFiles.storingPathDuration = ResultsFiles.tempCombinedColorDuration;
            ResultsFiles.storingPathRegular  = ResultsFiles.tempCombinedColorRegular;
            Rect.indexShowColor = 0;
            confirmMatchKeys         = new String[]{ TrainingScreen.confirmMatchColor };
            confirmMatchMouseButtons = new int[]{ MouseEvent.BUTTON1 };
            TrainingScreen.manualText = manualText + confirmMatchKeys[0] + "  For Color Match";
        }
        if (!StartScreen.includeAudio && !StartScreen.includeColor && StartScreen.includePosition) {
            mode = Modes.POSITION;
            ResultsFiles.storingPathDuration = ResultsFiles.tempCombinedPositionDuration;
            ResultsFiles.storingPathRegular  = ResultsFiles.tempCombinedPositionRegular;
            Rect.indexShowPosition = 0;
            confirmMatchKeys         = new String[]{ TrainingScreen.confirmMatchPosition };
            confirmMatchMouseButtons = new int[]{ MouseEvent.BUTTON1 };
            TrainingScreen.manualText = manualText + confirmMatchKeys[0] + "  For Position Match";
        }
        if (StartScreen.includeAudio && StartScreen.includeColor && !StartScreen.includePosition) {
            mode = Modes.AUDIO_COLOR;
            ResultsFiles.storingPathDuration = ResultsFiles.tempCombinedAudioColorDuration;
            ResultsFiles.storingPathRegular  = ResultsFiles.tempCombinedAudioColorRegular;
            Rect.indexPlayAudio = 0; Rect.indexShowColor = 1;
            confirmMatchKeys         = new String[]{ TrainingScreen.confirmMatchAudio, TrainingScreen.confirmMatchColor };
            confirmMatchMouseButtons = new int[]{ MouseEvent.BUTTON3, MouseEvent.BUTTON1 };
            TrainingScreen.manualText = manualText + confirmMatchKeys[0] + "  For Audio Match\n"
                + manualText + confirmMatchKeys[1] + "  For Color Match";
        }
        if (StartScreen.includeAudio && !StartScreen.includeColor && StartScreen.includePosition) {
            mode = Modes.AUDIO_POSITION;
            ResultsFiles.storingPathDuration = ResultsFiles.tempCombinedAudioPositionDuration;
            ResultsFiles.storingPathRegular  = ResultsFiles.tempCombinedAudioPositionRegular;
            Rect.indexPlayAudio = 0; Rect.indexShowPosition = 1;
            confirmMatchKeys         = new String[]{ TrainingScreen.confirmMatchAudio, TrainingScreen.confirmMatchPosition };
            confirmMatchMouseButtons = new int[]{ MouseEvent.BUTTON3, MouseEvent.BUTTON1 };
            TrainingScreen.manualText = manualText + confirmMatchKeys[0] + "  For Audio Match\n"
                + manualText + confirmMatchKeys[1] + "  For Position Match";
        }
        if (!StartScreen.includeAudio && StartScreen.includeColor && StartScreen.includePosition) {
            mode = Modes.COLOR_POSITION;
            ResultsFiles.storingPathDuration = ResultsFiles.tempCombinedColorPositionDuration;
            ResultsFiles.storingPathRegular  = ResultsFiles.tempCombinedColorPositionRegular;
            Rect.indexShowPosition = 0;
            confirmMatchKeys         = new String[]{ TrainingScreen.confirmMatchColor, TrainingScreen.confirmMatchPosition };
            confirmMatchMouseButtons = new int[]{ MouseEvent.BUTTON3, MouseEvent.BUTTON1 };
            TrainingScreen.manualText = manualText + confirmMatchKeys[0] + "  For Color Match\n"
                + manualText + confirmMatchKeys[1] + "  For Position Match";
        }
    }
}
