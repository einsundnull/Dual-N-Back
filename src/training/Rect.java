package training;

import static files.WordFileClass.wordList;
import static lists.Lists.*;

import java.awt.Font;
import java.io.File;
import java.net.URL;

import javax.swing.SwingUtilities;

import language.LanguageClass;
import lists.Lists;
import logics.DurationTraining;
import logics.Results;
import screens.OptionMenu;
import screens.StartScreen;
import screens.TrainingScreen;
import window.CustomClock;
import window.StyleUtil;
import window.SwingLabel;
import window.SwingPanel;

public class Rect {

    static boolean allowToInterrupt;
    public static boolean allowToRegisterClicks = false;
    public static boolean confirmVisualMatchCol;
    private static boolean countTrials = true;
    public static boolean colorAndSound;
    public static boolean positionAndSound = true;
    public static boolean textAndSound     = false;
    public static boolean durationTraining;
    public static boolean excludeFourFromRandomIndex = true;

    public static SwingPanel outside;
    public static SwingPanel inside;
    public static SwingPanel root;
    public static SwingPanel rootII;

    public static int indexCreateCounter = 0;
    public static int indexPlayAudio;
    public static int indexShowPosition;
    public static int indexShowColor;
    public static int lengthTime;
    private static int tempIndexLang;

    public static int    nback = 1;
    public static int    randomIndexIncreaseFactor = 60;
    private static int   randomIndexIncreaseFactorDurationMode = 30;
    public static int    randomIndexLimit = 9;
    private static double rectSize  = 200;
    public static double rootIIHeight;
    public static double rootIIWidth;
    private static double fontSize  = 125;
    private static final String fontType = "Arial";
    private double height;
    private static double width;

    private Thread thread;

    public static void resetValuesOfRect() {
        allowToRegisterClicks  = false;
        TimerClass.paused      = true;
        TimerClass.interrupt   = true;
        TimerClass.counter     = 0;
        TimerClass.timer.purge();
        indexCreateCounter     = 0;
        Results.presentations  = 0;
        if (TimerClass.endSession) TimerClass.passedTrials = 1;
        for (int n = 0; n < Results.includedModes; n++) {
            progressIndexModes[n]      = 0;
            clickedToConfirmMatch[n]   = false;
            matchesMode[n]             = false;
            rightValueMode[n]          = 0;
            percentageList[n]          = 0;
        }
    }

    private static void setColorOfRectangle(int indexVisColor, int indexOfRect) {
        if (!textAndSound) {
            rect.get(indexOfRect).setStyle("-fx-background-color:" + colors[indexVisColor]);
        }
    }

    @SuppressWarnings("unused")
    private static void setTextOfRectangle(int indexVisColor) {
        rect.get(4).setText(wordList.get(indexVisColor));
    }

    private static void setRectangle() {
        inside  = new SwingPanel();
        inside.setBounds(3, 3, (int) rectSize, (int) rectSize);
        inside.setStyle("-fx-background-color: black");

        outside = new SwingPanel();
        outside.setBounds(
            (int)(rootIIWidth  / 2 - 103),
            (int)(rootIIHeight / 2 - 83),
            (int)(rectSize + 6), (int)(rectSize + 6));
        outside.setStyle("-fx-background-color: rgb(30,30,30)");

        outside.add(inside);
        rootII.add(outside);
        inside.setVisible(StartScreen.includeColor);
        outside.setVisible(StartScreen.includeColor);

        for (int i = 0; i < 9; i++) {
            SwingLabel lbl = new SwingLabel();
            lbl.setBounds(0, 0, (int) rectSize, (int) rectSize);
            lbl.setStyle("-fx-background-color:" + Lists.colors[TrainingScreen.colorIndex]);
            lbl.setVisible(false);
            lbl.setHorizontalAlignment(SwingLabel.CENTER);
            lbl.setFont(new Font(fontType, Font.PLAIN, (int) fontSize));
            rootII.add(lbl);
            rect.add(lbl);
        }
    }

    public Rect(SwingPanel rootPanel, double height, double width,
                Object sceneUnused, Object stageUnused) {
        Rect.root  = rootPanel;
        Rect.width = width;
        this.height = height - height * 0.05;

        setTimer();
        setRootII();
        setRectangle();
        alignRect();
        setColorList();
    }

    private void alignRect() {
        rect.get(0).setBounds((int)(rootIIWidth/2-100-10-250), (int)(rootIIHeight/2-100-10-170), (int)rectSize,(int)rectSize);
        rect.get(1).setBounds((int)(rootIIWidth/2-100),        (int)(rootIIHeight/2-40-195),      (int)rectSize,(int)rectSize);
        rect.get(2).setBounds((int)(rootIIWidth/2+160),        (int)(rootIIHeight/2-100-10-170),  (int)rectSize,(int)rectSize);
        rect.get(3).setBounds((int)(rootIIWidth/2-300),        (int)(rootIIHeight/2-80),           (int)rectSize,(int)rectSize);
        rect.get(4).setBounds((int)(rootIIWidth/2-100),        (int)(rootIIHeight/2-80),           (int)rectSize,(int)rectSize);
        rect.get(5).setBounds((int)(rootIIWidth/2+100),        (int)(rootIIHeight/2-80),           (int)rectSize,(int)rectSize);
        rect.get(6).setBounds((int)(rootIIWidth/2-100-10-250), (int)(rootIIHeight/2+120),          (int)rectSize,(int)rectSize);
        rect.get(7).setBounds((int)(rootIIWidth/2-100),        (int)(rootIIHeight/2+85),           (int)rectSize,(int)rectSize);
        rect.get(8).setBounds((int)(rootIIWidth/2+160),        (int)(rootIIHeight/2+120),          (int)rectSize,(int)rectSize);

        root.add(rootII);
    }

    private void setRootII() {
        rootII = new SwingPanel();
        rootIIWidth  = height;
        rootIIHeight = height;
        rootII.setBounds((int)((width - height) / 2), 0, (int) height, (int) height);
    }

    private synchronized void setTimer() {
        tempIndexLang = StartScreen.indexLang;
        TrainingScreen.training.getPrimaryStage().requestFocus();

        thread = new Thread(() -> {
            TimerClass.setTimerI(() -> SwingUtilities.invokeLater(() -> {

                if (indexCreateCounter == 0) {
                    if (Lists.indexes[0][nback + 1] != -1) {
                        Results.presentations++;
                    }
                }

                if (Results.presentations >= TimerClass.maxPresentations) {
                    if (countTrials) TimerClass.passedTrials++;
                    countTrials = false;

                    if (TimerClass.passedTrials > TimerClass.sessionLengthInTrials) {
                        TimerClass.endSession = true;
                    }

                    if (Results.presentations > TimerClass.maxPresentations) {
                        indexCreateCounter = 0;
                        TimerClass.paused  = true;
                        TimerClass.interrupt = true;
                        countTrials = true;
                        TimerClass.counter = 0;
                        TimerClass.timer.purge();
                        Results.executeTrialResults();
                        TrainingScreen.setInterruptionScreen();
                        indexes   = createIndexes(indexes);
                        clicksList = createClickList(clicksList);
                        if (StartScreen.includePosition) {
                            rect.get(randomIndex[indexShowPosition]).setVisible(false);
                        }
                        rect.get(4).setVisible(false);
                        if (TrainingScreen.setCustomClock) CustomClock.cancelTimer();
                    }

                } else {
                    indexCreateCounter++;
                    if (Results.presentations == TimerClass.maxPresentations) allowToInterrupt = true;

                    if (indexCreateCounter == 1) {
                        if (Lists.indexes[0][nback + 1] != -1) {
                            clickedRightMode = Results.registerClicks(matchesMode, clickedToConfirmMatch, clickedRightMode);
                            rightValueMode   = Results.increaseRightValue(clickedRightMode, rightValueMode);
                            try {
                                progressBars = DurationTraining.colorMatches(clickedRightMode, progressIndexModes, progressBars);
                            } catch (Exception ignored) {}
                            clicksList = DurationTraining.storeDurationInput(clickedRightMode, clicksList);
                            for (int n = 0; n < Results.includedModes; n++) {
                                clickedToConfirmMatch[n] = false;
                            }
                        }

                        randomIndex  = Results.createRandomIndex(randomIndex);
                        if (!StartScreen.durationTraining) {
                            Lists.indexes = Results.storePresentedIndex(Lists.indexes, randomIndex, randomIndexIncreaseFactor);
                        } else {
                            Lists.indexes = Results.storePresentedIndex(Lists.indexes, randomIndex, randomIndexIncreaseFactorDurationMode);
                        }
                        matchesMode = Results.registerMatches(randomIndex, Lists.indexes, matchesMode);

                        if (StartScreen.includeAudio) {
                            int audIdx = randomIndex[indexPlayAudio];
                            boolean useCustom = OptionMenu.chooseAudioFileBooleanArray[audIdx];
                            String  customFile = OptionMenu.audioFileArray[audIdx];
                            if (useCustom && customFile != null && !customFile.isEmpty() && !customFile.equals("#")) {
                                AudioClass.playFile(customFile + ".wav");
                            } else {
                                int lang = fallbackLang(tempIndexLang);
                                String path = "/audio/" + LanguageClass.word(lang, 28) + audIdx + ".mp3";
                                URL url = AudioClass.class.getResource(path);
                                if (url != null) AudioClass.playAsync(url);
                            }
                        }

                        if (StartScreen.includeColor && !StartScreen.includePosition) {
                            setColorOfRectangle(randomIndex[indexShowColor], 4);
                            rect.get(4).setVisible(true);
                        }
                        if (!StartScreen.includeColor && StartScreen.includePosition) {
                            rect.get(randomIndex[indexShowPosition]).setVisible(true);
                        }
                        if (StartScreen.includePosition && StartScreen.includeColor) {
                            setColorOfRectangle(randomIndex[indexShowColor], randomIndex[indexShowPosition]);
                            rect.get(randomIndex[indexShowPosition]).setVisible(true);
                        }

                        allowToRegisterClicks = true;
                        allowToInterrupt      = false;
                    }

                    if (indexCreateCounter == 2) {
                        indexCreateCounter = 0;
                        if (StartScreen.includePosition) {
                            rect.get(randomIndex[indexShowPosition]).setVisible(false);
                        }
                        rect.get(4).setVisible(false);
                    }
                }

                // repaint the training panel
                if (root != null) root.repaint();
            }));
        });
        thread.setDaemon(true);
        thread.start();
    }

    /** Languages without dedicated audio fall back to English (index 3). */
    private static int fallbackLang(int lang) {
        return switch (lang) {
            case 1,2,3,6,7,9,11,12,13,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30 -> 3;
            default -> lang;
        };
    }

    public static void showRectangles(boolean value) {
        for (int i = 0; i < rect.size(); i++) rect.get(i).setVisible(value);
        rect.get(4).setVisible(false);
    }
}
