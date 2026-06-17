package screens;

import static files.ResultsFiles.*;
import static lists.Lists.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.*;

import enums.Modes;
import enums.TypeOfWindow;
import files.FilesCls;
import files.ResultsFiles;
import language.LanguageClass;
import lists.Lists;
import logics.DurationTraining;
import logics.Results;
import training.Rect;
import training.TimerClass;
import window.*;

public class TrainingScreen {

    public static CustomWindow training;
    private static CustomButton trainingExitButton;
    public static SwingPanel    root;

    private static SwingPanel[] cross = new SwingPanel[2];
    private static SwingPanel[] passedTrialsInfoPane;

    public static SwingLabel lbl_n_Back          = new SwingLabel();
    public static SwingLabel lbl_n_back_Info_Pause;
    private static SwingLabel lblPause           = new SwingLabel();
    private static SwingLabel lblESC             = new SwingLabel();
    private static SwingLabel lbl_PressSpace     = new SwingLabel();
    private static SwingLabel lbl_Results        = new SwingLabel();
    private static SwingLabel manual;

    private static String PAUSED, PRESS_ESC, NBackLEVEL;
    private static String YOUR_RATE_AUD, YOUR_RATE_COL, YOUR_RATE_POS;
    private static String PRESS_SPACE, EXIT, _back;
    private static String ID_I, ID_II;

    public static String confirmMatchAudio    = "L";
    public static String confirmMatchPosition = "S";
    public static String confirmMatchColor    = "D";

    public static double width, height;
    private static double minutesToTrials;
    public static int  durationVariationOnProgressBar = 60000;
    public static int  trialID;
    public static int  durationOfTranslation;
    public static int  durationInPresentations;
    public static int  durationOfTranslationToSet;
    public static int  colorIndex = 0;

    private static boolean exit         = false;
    public  static boolean showPRESS_SPACE = true;
    private static boolean exitFromButton;
    public  static boolean firstStart   = true;
    private static boolean rectsAreVisible;
    public  static boolean setCustomClock;
    public  static String  manualText   = "";

    private static final Cursor BLANK_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(
        new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(), "blank");

    public TrainingScreen() {
        StartScreen.trainingStarted = true;
        width  = StartScreen.screenWidth;
        height = StartScreen.screenHeight;
        TimerClass.endSession = false;

        training = new CustomWindow(TypeOfWindow.SECONDARYSTAGE, width, height);
        training.setStyle("-fx-background-color: black");
        training.setFullScreen(true);
        training.hideCursor(true);
        training.setResizeable(false);

        root = training.getFront();          // content layer (getFrame() = outer panel was hidden behind it)
        root.setLayout(null);
        root.setStyle("-fx-background-color: black");
        root.setBackground(Color.BLACK);

        indexes    = createIndexes(indexes);
        clicksList = createClickList(clicksList);

        getLanguage(StartScreen.indexLang);
        setMessageScreen();
        setExitButton();
        setCross();

        new Rect(root, height, width, null, null);
        root.add(OptionMenu.drop.newDropMenu());

        exit = false;
        setPauseLabel();
        setKeyListener();
        setOnMouseClicked();
        training.show();
        startTrial();
    }

    public TrainingScreen(String empty) {}

    // -----------------------------------------------------------------------
    private void setCross() {
        for (int i = 0; i < 2; i++) cross[i] = new SwingPanel();

        int cx = (int)(StartScreen.screenWidth / 2);
        int cy = (int)(StartScreen.screenHeight / 2);
        int sw = (int)(StartScreen.screenWidth * 0.009375);
        int sh = (int)(StartScreen.screenWidth * 0.0015625);

        cross[0].setBounds(cx - sw / 2, cy - sh / 2, sw, sh);
        cross[0].setStyle("-fx-background-color: blanchedalmond");

        cross[1].setBounds(cx - sh / 2, cy - sw / 2, sh, sw);
        cross[1].setStyle("-fx-background-color: blanchedalmond");

        root.add(cross[0]);
        root.add(cross[1]);
    }

    private void setExitButton() {
        trainingExitButton = new CustomButton(
            StartScreen.screenWidth * 0.0625,
            StartScreen.screenWidth * 0.01875);
        trainingExitButton.setLayout(
            width - StartScreen.screenWidth * 0.08125,
            StartScreen.screenWidth * 0.0125);
        trainingExitButton.setText(EXIT);
        trainingExitButton.setVisible(false);
        trainingExitButton.setOnMouseClicked(() -> {
            TimerClass.endSession = true;
            exitFromButton = true;
            endSession();
        });
        training.setExitButtonOnAction(() -> {
            TimerClass.endSession = true;
            exitFromButton = true;
            endSession();
        });
        root.add(trainingExitButton.getFrame());
    }

    private void setMessageScreen() {
        Font big    = new Font("Arial", Font.PLAIN, (int)(StartScreen.screenWidth * 0.05));
        Font small  = new Font("Arial", Font.PLAIN, (int)(StartScreen.screenWidth * 0.015625));
        Font medium = new Font("Arial", Font.PLAIN, (int)(StartScreen.screenWidth * 0.025));

        lbl_n_Back = new SwingLabel(Rect.nback + _back);
        lbl_n_Back.setForeground(Color.WHITE);
        lbl_n_Back.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_n_Back.setFont(big);
        lbl_n_Back.setBounds(0, (int)(height * 0.5 - StartScreen.screenWidth * 0.09375),
                             (int) width, (int)(StartScreen.screenWidth * 0.09375));
        lbl_n_Back.setVisible(false);
        root.add(lbl_n_Back);

        lbl_PressSpace = new SwingLabel(PRESS_SPACE);
        lbl_PressSpace.setForeground(Color.WHITE);
        lbl_PressSpace.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_PressSpace.setFont(small);
        lbl_PressSpace.setBounds(0, (int)(height * 0.5 + StartScreen.screenWidth * 0.15625),
                                 (int) width, (int)(StartScreen.screenWidth * 0.09375));
        lbl_PressSpace.setVisible(false);
        root.add(lbl_PressSpace);

        lbl_Results = new SwingLabel();
        lbl_Results.setForeground(Color.WHITE);
        lbl_Results.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Results.setFont(medium);
        lbl_Results.setBounds(0, (int)(height * 0.5 - StartScreen.screenWidth * 0.09375),
                              (int) width, (int)(StartScreen.screenWidth * 0.125));
        lbl_Results.setVisible(false);
        root.add(lbl_Results);

        String len = StartScreen.flds.get(1).getText().replaceAll("[^0-9]", "");
        if (len.isEmpty() || len.equals("0")) len = "1";
        minutesToTrials = Double.parseDouble(len);

        int size = (int) minutesToTrials;
        if (size % 2 != 0) size++;
        passedTrialsInfoPane = new SwingPanel[size];
        double corrector = (size != (int) minutesToTrials) ? 0 : StartScreen.screenWidth * 0.00625;
        double pos = width / 2 - (40 * (minutesToTrials / 4));

        for (int i = 0; i < size; i++) {
            passedTrialsInfoPane[i] = new SwingPanel();
            passedTrialsInfoPane[i].setBounds(
                (int)(pos + corrector + i * (StartScreen.screenWidth * 0.025)),
                (int)(height / 2 + StartScreen.screenWidth * 0.015625),
                (int)(StartScreen.screenWidth * 0.0125),
                (int)(StartScreen.screenWidth * 0.008125));
            passedTrialsInfoPane[i].setVisible(false);
            root.add(passedTrialsInfoPane[i]);
        }

        if (!StartScreen.durationTraining) {
            for (int i = 0; i < minutesToTrials / 2; i++) {
                passedTrialsInfoPane[i].setStyle("-fx-background-color: rgb(245,245,245)");
            }
            passedTrialsInfoPane[0].setStyle("-fx-background-color: red");
        }
    }

    private void setPauseLabel() {
        Font pauseFont = new Font("Arial", Font.PLAIN, (int)(StartScreen.screenWidth * 0.025));
        Font escFont   = new Font("Arial", Font.PLAIN, (int)(StartScreen.screenWidth * 0.01875));
        Font infoFont  = new Font("Arial", Font.PLAIN, (int)(StartScreen.screenWidth * 0.05));

        lblPause = new SwingLabel(PAUSED);
        lblPause.setForeground(Color.WHITE);
        lblPause.setHorizontalAlignment(SwingConstants.CENTER);
        lblPause.setFont(pauseFont);
        lblPause.setBounds(
            (int)(width / 2 - StartScreen.screenWidth * 0.21875),
            (int)(height / 7 - StartScreen.screenWidth * 0.01875),
            (int)(StartScreen.screenWidth * 0.4375),
            (int)(StartScreen.screenWidth * 0.125));
        lblPause.setVisible(false);
        root.add(lblPause);

        lblESC = new SwingLabel(PRESS_ESC);
        lblESC.setForeground(Color.WHITE);
        lblESC.setHorizontalAlignment(SwingConstants.CENTER);
        lblESC.setFont(escFont);
        lblESC.setBounds(
            (int)(width / 2 - StartScreen.screenWidth * 0.21875),
            (int)(height / 1.8),
            (int)(StartScreen.screenWidth * 0.4375),
            (int)(StartScreen.screenWidth * 0.125));
        lblESC.setVisible(false);
        root.add(lblESC);

        lbl_n_back_Info_Pause = new SwingLabel(NBackLEVEL);
        lbl_n_back_Info_Pause.setForeground(Color.WHITE);
        lbl_n_back_Info_Pause.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_n_back_Info_Pause.setFont(infoFont);
        lbl_n_back_Info_Pause.setBounds(0,
            (int)(height * 0.5 - StartScreen.screenWidth * 0.09375),
            (int) width,
            (int)(StartScreen.screenWidth * 0.09375));
        lbl_n_back_Info_Pause.setVisible(false);
        root.add(lbl_n_back_Info_Pause);
    }

    // -----------------------------------------------------------------------
    public static void setInterruptionScreen() {
        if (!firstStart && !exitFromButton) {
            Rect.showRectangles(false);
            cross[0].setVisible(false);
            cross[1].setVisible(false);
            Rect.outside.setVisible(false);
            setResultsOfTrialToLabel();
            DurationTraining.colorRightClicks();
            DurationTraining.setProgressBarVisible();
            if (setCustomClock) CustomClock.cancelTimer();
            DurationTraining.resetProgressBarSize(true);
            lbl_Results.setVisible(true);
            lbl_PressSpace.setText(LanguageClass.word(StartScreen.indexLang, 49));
            lbl_PressSpace.setVisible(true);
        } else if (TimerClass.endSession && !exitFromButton) {
            endSession();
            for (SwingPanel p : passedTrialsInfoPane)
                p.setStyle("-fx-background-color: rgb(245,245,245)");
        }
        exitFromButton = false;
        Rect.resetValuesOfRect();
    }

    private static void setResultsOfTrialToLabel() {
        int[] pct = new int[Results.includedModes];
        for (int n = 0; n < Results.includedModes; n++) {
            pct[n] = (int)(percentageList[n] * 100);
        }
        lbl_Results.setBounds(lbl_Results.getX(), lbl_Results.getY(),
                              (int) width, (int)(StartScreen.screenWidth * 0.125));
        String text = buildResultsText(pct);
        lbl_Results.setText("<html>" + text.replace("\n", "<br>") + "</html>");
    }

    private static String buildResultsText(int[] pct) {
        return switch (Results.mode) {
            case AUDIO_COLOR_POSITION -> YOUR_RATE_POS + " " + pct[Rect.indexShowPosition] + "%\n\n"
                + YOUR_RATE_AUD + " " + pct[Rect.indexPlayAudio] + "%\n\n"
                + YOUR_RATE_COL + " " + pct[Rect.indexShowColor] + "%";
            case AUDIO_COLOR   -> YOUR_RATE_COL + " " + pct[Rect.indexShowColor] + "%\n\n"
                + YOUR_RATE_AUD + " " + pct[Rect.indexPlayAudio] + "%";
            case AUDIO_POSITION -> YOUR_RATE_POS + " " + pct[Rect.indexShowPosition] + "%\n\n"
                + YOUR_RATE_AUD + " " + pct[Rect.indexPlayAudio] + "%";
            case COLOR_POSITION -> YOUR_RATE_COL + " " + pct[Rect.indexShowColor] + "%\n\n"
                + YOUR_RATE_POS + " " + pct[Rect.indexShowPosition] + "%";
            case AUDIO    -> YOUR_RATE_AUD + " " + pct[Rect.indexPlayAudio] + "%";
            case COLOR    -> YOUR_RATE_COL + " " + pct[Rect.indexShowColor] + "%";
            case POSITION -> YOUR_RATE_POS + " " + pct[Rect.indexShowPosition] + "%";
        };
    }

    // -----------------------------------------------------------------------
    private static synchronized void setKeyListener() {
        JFrame frame = training.getScene();
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() != KeyEvent.KEY_PRESSED) return false;
            int code = e.getKeyCode();

            if (code == KeyEvent.VK_PLUS || code == KeyEvent.VK_ADD) {
                rectsAreVisible = !rectsAreVisible;
                Rect.showRectangles(rectsAreVisible);
            }
            if (code == KeyEvent.VK_MINUS || code == KeyEvent.VK_SUBTRACT) {
                Rect.textAndSound = !Rect.textAndSound;
            }
            if (code == KeyEvent.VK_PRINTSCREEN) {
                TimerClass.repeatTime = 900;
            }
            if (code == KeyEvent.VK_PAGE_UP) {
                TimerClass.repeatTime += 15;
            }
            if (code == KeyEvent.VK_PAGE_DOWN) {
                TimerClass.repeatTime -= 15;
            }
            if (code == KeyEvent.VK_F1) {
                OptionMenu.drop.playAndBack();
                if (OptionMenu.drop.isPlay()) {
                    OptionMenu.drop.getContainerPane().toFront();
                    frame.setCursor(Cursor.getDefaultCursor());
                } else {
                    root.requestFocusInWindow();
                }
            }
            if (code == KeyEvent.VK_F2) {
                if (!StartScreen.includeColor) {
                    colorIndex = (colorIndex + 1) % Lists.colors.length;
                    for (SwingLabel r : Lists.rect)
                        r.setStyle("-fx-background-color:" + Lists.colors[colorIndex]);
                    FilesCls.storeUserSettings();
                }
                if (StartScreen.includePosition)
                    Lists.rect.get(Rect.indexShowPosition).setVisible(true);
            }
            if (code == KeyEvent.VK_ESCAPE) {
                handleEscape(frame);
            }
            if (code == KeyEvent.VK_SPACE && !lblESC.isVisible()) {
                Rect.showRectangles(false);
                pressSpaceLogic();
            }

            // match confirm keys
            clickedToConfirmMatch = confirmMatchKeyLogic(e.getKeyChar(), clickedToConfirmMatch);
            return false;
        });
    }

    private static void handleEscape(JFrame frame) {
        if (!exit && !TimerClass.endSession) {
            Rect.outside.setVisible(false);
            lblPause.setVisible(true);
            if (setCustomClock) CustomClock.pauseTimer(true);
            lblESC.setVisible(true);
            if (!lbl_n_Back.isVisible() && !lbl_Results.isVisible()) {
                lbl_n_back_Info_Pause.setVisible(true);
                for (SwingPanel p : passedTrialsInfoPane) p.setVisible(true);
            }
            Rect.showRectangles(false);
            lbl_PressSpace.setVisible(false);
            trainingExitButton.setVisible(true);
            TimerClass.paused = true;
            exit = true;
            frame.setCursor(Cursor.getDefaultCursor());
        } else if (exit) {
            lblPause.setVisible(false);
            lblESC.setVisible(false);
            if (setCustomClock) CustomClock.pauseTimer(false);
            Rect.showRectangles(false);
            if (!TimerClass.interrupt) {
                lbl_n_back_Info_Pause.setVisible(false);
                for (SwingPanel p : passedTrialsInfoPane) p.setVisible(false);
                Rect.outside.setVisible(false);
            }
            trainingExitButton.setVisible(false);
            if (lbl_Results.isVisible() && showPRESS_SPACE) lbl_PressSpace.setVisible(true);
            frame.setCursor(BLANK_CURSOR);
            if (!StartScreen.includePosition && StartScreen.includeColor
                    && !TimerClass.endSession && !lbl_n_Back.isVisible() && !lbl_Results.isVisible()) {
                Rect.outside.setVisible(true);
            }
            TimerClass.paused = false;
            exit = false;
        }
    }

    private static boolean[] confirmMatchKeyLogic(char ch, boolean[] clicked) {
        for (int n = 0; n < Results.includedModes; n++) {
            if (confirmMatchKeys[n] != null &&
                confirmMatchKeys[n].equalsIgnoreCase(String.valueOf(ch)) && !clicked[n]) {
                clicked[n] = true;
            }
        }
        return clicked;
    }

    private static void setOnMouseClicked() {
        training.getScene().addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                clickedToConfirmMatch = confirmMatchMouseLogic(e.getButton(), clickedToConfirmMatch);
            }
        });
    }

    private static boolean[] confirmMatchMouseLogic(int button, boolean[] clicked) {
        if (!TimerClass.endSession && !lbl_Results.isVisible()) {
            for (int n = 0; n < Results.includedModes; n++) {
                if (button == confirmMatchMouseButtons[n]) clicked[n] = true;
            }
        } else {
            pressSpaceLogic();
        }
        return clicked;
    }

    private static void pressSpaceLogic() {
        if (TimerClass.endSession) {
            endSession();
            StartScreen.disableButtons(false);
            if (StartScreen.options != null && OptionMenu.drop.isPlay()) OptionMenu.drop.back();
        }
        if (lbl_Results.isVisible() && !TimerClass.endSession) {
            lbl_Results.setVisible(false);
            lbl_PressSpace.setVisible(false);
            lbl_n_Back.setVisible(true);
            DurationTraining.resetProgressBar();
            DurationTraining.setProgressBarVisible();
            DurationTraining.resetProgressBarSize(false);
            if (!StartScreen.durationTraining) {
                for (SwingPanel p : passedTrialsInfoPane) p.setVisible(true);
                if (TimerClass.passedTrials > 0)
                    passedTrialsInfoPane[TimerClass.passedTrials - 1].setStyle("-fx-background-color: red");
                if (TimerClass.passedTrials > 1)
                    passedTrialsInfoPane[TimerClass.passedTrials - 2].setStyle("-fx-background-color: green");
                if (TimerClass.passedTrials >= TimerClass.sessionLengthInTrials)
                    passedTrialsInfoPane[TimerClass.passedTrials - 1].setStyle("-fx-background-color: green");
            }
        } else if (lbl_n_Back.isVisible()) {
            training.getScene().setCursor(BLANK_CURSOR);
            if (StartScreen.includeColor && !StartScreen.includePosition) Rect.outside.setVisible(true);
            if (setCustomClock) { CustomClock.pauseTimer(false); CustomClock.startClock(); }
            lbl_Results.setVisible(false);
            lbl_PressSpace.setVisible(false);
            lbl_n_Back.setVisible(false);
            manual.setVisible(false);
            for (SwingPanel p : passedTrialsInfoPane) p.setVisible(false);
            if (!firstStart) {
                TimerClass.interrupt = false;
                TimerClass.paused    = false;
                showPRESS_SPACE      = false;
            }
            setOrientationPointsVisible();
        }
        training.getPrimaryStage().requestFocus();
    }

    private static void startTrial() {
        if (firstStart) {
            FilesCls.createDataDirectory();

            manual = new SwingLabel("<html>" + manualText.replace("\n","<br>") + "</html>");
            manual.setForeground(Color.WHITE);
            manual.setFont(new Font("Arial", Font.PLAIN, (int)(StartScreen.screenWidth * 0.015625)));
            manual.setBounds(
                (int)(StartScreen.screenWidth * 0.01),
                (int)(StartScreen.screenWidth * 0.01875),
                600, 100);
            root.add(manual);

            progressBars = createProgressBar(height, Lists.progressBars);
            Lists.createTransitionList();
            for (int n = 0; n < Results.includedModes; n++) {
                for (SwingPanel bar : progressBars.get(n)) root.add(bar);
            }
        }
        firstStart = false;
        lbl_Results.setVisible(false);
        lbl_n_Back.setText(Rect.nback + _back);
        lbl_n_Back.setVisible(true);
        lbl_PressSpace.setVisible(true);
        for (SwingPanel p : passedTrialsInfoPane) p.setVisible(true);
        DurationTraining.setProgressBarVisible();
        DurationTraining.resetProgressBarSize(false);
    }

    public static void setOrientationPointsVisible() {
        boolean posMode = Results.mode == Modes.AUDIO_COLOR_POSITION
            || Results.mode == Modes.AUDIO_POSITION
            || Results.mode == Modes.COLOR_POSITION
            || Results.mode == Modes.POSITION;
        if (posMode && cross != null) {
            boolean vis = !(TimerClass.interrupt && !TimerClass.paused);
            cross[0].setVisible(vis);
            cross[1].setVisible(vis);
        }
    }

    public static void endSession() {
        try {
            if (!exitFromButton) {
                StartScreen.btns.get(4).setStyle("-fx-background-color: lightblue");
                StartScreen.btns.get(4).setDisable(false);
                if (!TimerClass.endSession) combinedFileWriteLogic();
            }
            TimerClass.timer.cancel();
            if (setCustomClock) CustomClock.cancelTimer();

            Rect.rootII.removeAll();
            Rect.root.removeAll();
            lbl_Results.setVisible(true);
            lbl_PressSpace.setVisible(true);
            exitFromButton = false;
            firstStart     = true;
            Rect.resetValuesOfRect();
            StartScreen.trainingStarted = false;
            SwingUtilities.invokeLater(() -> {
                StartScreen.mainWindow.getFront().requestFocusInWindow();
                OptionMenu.setOptionMenu();
                training.close();
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getLanguage(int index) {
        EXIT        = LanguageClass.word(index, 20);
        PAUSED      = LanguageClass.word(index, 21);
        PRESS_ESC   = LanguageClass.word(index, 22);
        NBackLEVEL  = Rect.nback + LanguageClass.word(index, 25);
        PRESS_SPACE = LanguageClass.word(index, 37);
        YOUR_RATE_COL = LanguageClass.word(index, 23);
        YOUR_RATE_AUD = LanguageClass.word(index, 24);
        YOUR_RATE_POS = LanguageClass.word(index, 23); // same key as color in original
        _back         = LanguageClass.word(index, 25);
    }

    public static void combinedFileWriteLogic() {
        String day = ResultsFiles.getDay();
        String sid = String.valueOf(new Random().nextInt(99999999));
        ID_I  = "date:\n" + day + "\nn-back:\n" + Rect.nback + "\n";
        ID_II = "\nsessionID:\n" + sid + "\nday:\n" + day + "\n";
        writeToProgressArray();
        writeToOverallPercentage();
    }

    private static void writeToOverallPercentage() {
        String path = StartScreen.durationTraining ? storingPathDuration : storingPathRegular;
        writeToFile(ID_I + "percentage: " + Results.overallPercentage + ID_II, path);
    }

    private static void writeToProgressArray() {
        switch (Results.mode) {
            case AUDIO_COLOR_POSITION -> {
                if (StartScreen.durationTraining) {
                    writeToFile(ID_I + clicksList.get(Rect.indexPlayAudio)    + ID_II, tempAudioDuration);
                    writeToFile(ID_I + clicksList.get(Rect.indexShowColor)    + ID_II, tempColorDuration);
                    writeToFile(ID_I + clicksList.get(Rect.indexShowPosition) + ID_II, tempPositionDuration);
                }
                writeToFile(ID_I + "percentage:\n" + percentageList[Rect.indexPlayAudio]    + ID_II, tempAudioDurationPercentage);
                writeToFile(ID_I + "percentage:\n" + percentageList[Rect.indexShowColor]    + ID_II, tempColorDurationPercentage);
                writeToFile(ID_I + "percentage:\n" + percentageList[Rect.indexShowPosition] + ID_II, tempPositionDurationPercentage);
            }
            case AUDIO_COLOR -> {
                if (StartScreen.durationTraining) {
                    writeToFile(ID_I + clicksList.get(Rect.indexPlayAudio)  + ID_II, tempAudioDuration);
                    writeToFile(ID_I + clicksList.get(Rect.indexShowColor)  + ID_II, tempColorDuration);
                }
                writeToFile(ID_I + "percentage:\n" + percentageList[Rect.indexPlayAudio]  + ID_II, tempAudioDurationPercentage);
                writeToFile(ID_I + "percentage:\n" + percentageList[Rect.indexShowColor]  + ID_II, tempColorDurationPercentage);
            }
            case AUDIO_POSITION -> {
                if (StartScreen.durationTraining) {
                    writeToFile(ID_I + clicksList.get(Rect.indexPlayAudio)    + ID_II, tempAudioDuration);
                    writeToFile(ID_I + clicksList.get(Rect.indexShowPosition) + ID_II, tempPositionDuration);
                }
                writeToFile(ID_I + "percentage:\n" + percentageList[Rect.indexPlayAudio]    + ID_II, tempAudioDurationPercentage);
                writeToFile(ID_I + "percentage:\n" + percentageList[Rect.indexShowPosition] + ID_II, tempPositionDurationPercentage);
            }
            case COLOR_POSITION -> {
                if (StartScreen.durationTraining) {
                    writeToFile(ID_I + clicksList.get(Rect.indexShowColor)    + ID_II, tempColorDuration);
                    writeToFile(ID_I + clicksList.get(Rect.indexShowPosition) + ID_II, tempPositionDuration);
                }
                writeToFile(ID_I + "percentage:\n" + percentageList[Rect.indexShowColor]    + ID_II, tempColorDurationPercentage);
                writeToFile(ID_I + "percentage:\n" + percentageList[Rect.indexShowPosition] + ID_II, tempPositionDurationPercentage);
            }
            case AUDIO -> {
                if (StartScreen.durationTraining)
                    writeToFile(ID_I + clicksList.get(Rect.indexPlayAudio) + ID_II, tempAudioDuration);
                writeToFile(ID_I + "percentage:\n" + percentageList[Rect.indexPlayAudio] + ID_II, tempAudioDurationPercentage);
            }
            case COLOR -> {
                if (StartScreen.durationTraining)
                    writeToFile(ID_I + clicksList.get(Rect.indexShowColor) + ID_II, tempColorDuration);
                writeToFile(ID_I + "percentage:\n" + percentageList[Rect.indexShowColor] + ID_II, tempColorDurationPercentage);
            }
            case POSITION -> {
                if (StartScreen.durationTraining)
                    writeToFile(ID_I + clicksList.get(Rect.indexShowPosition) + ID_II, tempPositionDuration);
                writeToFile(ID_I + "percentage:\n" + percentageList[Rect.indexShowPosition] + ID_II, tempPositionDurationPercentage);
            }
        }
    }
}
