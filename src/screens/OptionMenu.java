package screens;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import enums.TypeOfDropMenu;
import files.FilesCls;
import logics.DurationTraining;
import training.TimerClass;
import window.CustomButton;
import window.CustomDropMenu;
import window.CustomPlayAudioButton;
import window.CustomRecordButton;
import window.CustomWindow2;

public class OptionMenu {

    public static int numberOfRecordButtons = 9;
    public static CustomDropMenu drop;

    public static boolean[] chooseAudioFileBooleanArray = new boolean[numberOfRecordButtons];
    public static String[]  audioFileArray              = new String[numberOfRecordButtons];

    private static ArrayList<JPanel>     audioBtnList              = new ArrayList<>();
    private static ArrayList<JCheckBox>  optionCheckBoxArray       = new ArrayList<>();
    private static ArrayList<JCheckBox>  chooseAudioFileCheckBoxes = new ArrayList<>();
    private static ArrayList<JTextField> confirmButtonTextFields   = new ArrayList<>();
    private static ArrayList<JLabel>     confirmInfoLabels         = new ArrayList<>();

    private static final double width    = 600;
    private static final double btnWidth = 60;
    private static final double btnHeight= 30;
    private static final double layoutX  = 30;
    private static final double recordBtnLayoutX   = layoutX + btnWidth + 10;
    private static final double playButtonLayoutX  = recordBtnLayoutX + 70;

    private static String filepath = "";
    private static final String fileName      = "tone";
    private static final String fileFormat    = ".wav";
    private static final AudioFileFormat.Type audiFileFormat = AudioFileFormat.Type.WAVE;

    public OptionMenu() {
        drop = new CustomDropMenu(TypeOfDropMenu.FROM_TOP, true);
        drop.getContentPane().setStyle("-fx-background-color: grey");
        drop.setPrefSize(width, CustomWindow2.getFrontHeight());

        filepath = FilesCls.programPath + File.separator
                 + StartScreen.user + "_data" + File.separator + "AUDIO_FILES";

        audioBtnList.clear();
        optionCheckBoxArray.clear();
        chooseAudioFileCheckBoxes.clear();
        confirmButtonTextFields.clear();
        confirmInfoLabels.clear();

        setButtonsChooseAudioFile();
        setButtonsRecord();
        setButtonsPlay();
        setCheckBoxesChooseAudioFile();
        setOptionCheckBoxes();
        setConfirmButtonTextFields();
        setConfirmButtonInfoText();
        setTextToConfirmButtonTextFields();

        setOptionCheckBoxesSelected();
        setCheckBoxesChooseAudioFileSelected();
    }

    private static void setButtonsChooseAudioFile() {
        for (int i = 0; i < numberOfRecordButtons; i++) {
            CustomButton btn = new CustomButton(btnWidth, btnHeight);
            btn.setLayout(layoutX, btnHeight * i + 10);
            btn.setText("File");
            final int idx = i;
            btn.setOnMouseClicked(() -> {
                JFileChooser ch = new JFileChooser(new File(FilesCls.audioFilePath));
                if (ch.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    audioFileArray[idx] = ch.getSelectedFile().getAbsolutePath().replace(".wav","");
                    chooseAudioFileBooleanArray[idx] = true;
                    FilesCls.writeAudioArrayFile();
                }
            });
            audioBtnList.add(btn.newButton());
        }
    }

    private static void setButtonsRecord() {
        for (int i = 0; i < numberOfRecordButtons; i++) {
            CustomRecordButton btn = new CustomRecordButton();
            btn.setLayoutY(btnHeight * i + 10);
            btn.setID("" + i);
            btn.setLayoutX(recordBtnLayoutX);
            btn.setAudioFile(filepath, fileName, "" + i, fileFormat, audiFileFormat);
            final int idx = i;
            btn.setRunFunctions(() -> {
                audioFileArray[idx] = filepath + File.separator + fileName + idx;
                btn.setAudioFile(FilesCls.audioFilePath, fileName, "" + idx, fileFormat, audiFileFormat);
                FilesCls.writeAudioArrayFile();
            }, null);
            audioBtnList.add(btn.newButton());
        }
    }

    private static void setButtonsPlay() {
        for (int i = 0; i < numberOfRecordButtons; i++) {
            CustomPlayAudioButton btn = new CustomPlayAudioButton();
            btn.setLayoutY(btnHeight * i + 10);
            btn.setID("" + i);
            btn.setLayoutX(playButtonLayoutX);
            btn.setAudioFile(filepath, fileName, "" + i, fileFormat);
            final int idx = i;
            btn.setRunFunctions(() -> btn.setAudioFile(FilesCls.audioFilePath, fileName, "" + idx, fileFormat));
            audioBtnList.add(btn.newButton());
        }
    }

    private static void setCheckBoxesChooseAudioFile() {
        for (int i = 0; i < numberOfRecordButtons; i++) {
            JCheckBox cb = new JCheckBox();
            cb.setBounds((int)(playButtonLayoutX + btnWidth + 10), (int)(btnHeight * i + 10), 30, 30);
            final int idx = i;
            cb.addActionListener(e -> {
                chooseAudioFileBooleanArray[idx] = !chooseAudioFileBooleanArray[idx];
                FilesCls.storeUserSettings();
            });
            chooseAudioFileCheckBoxes.add(cb);
        }
    }

    private static void setOptionCheckBoxes() {
        double yBase = numberOfRecordButtons * btnHeight + 30;
        String[] texts = {
            "Progress bar: duration training",
            "Progress bar: regular training",
            "Show right/false clicks in bar",
            "Show progress immediately"
        };
        for (int i = 0; i < texts.length; i++) {
            JCheckBox cb = new JCheckBox(texts[i]);
            cb.setBounds((int)layoutX, (int)(yBase + btnHeight * i + 10), 500, 30);
            final int idx = i;
            cb.addActionListener(e -> {
                switch (idx) {
                    case 0 -> DurationTraining.showProgressBarDurationTraining   = !DurationTraining.showProgressBarDurationTraining;
                    case 1 -> DurationTraining.showProgressBarRegularTraining     = !DurationTraining.showProgressBarRegularTraining;
                    case 2 -> DurationTraining.showRightAndFalseClicksInProgressBar = !DurationTraining.showRightAndFalseClicksInProgressBar;
                    case 3 -> DurationTraining.showRightClicksImediately          = !DurationTraining.showRightClicksImediately;
                }
            });
            optionCheckBoxArray.add(cb);
        }
    }

    private static void setConfirmButtonTextFields() {
        double yBase = CustomWindow2.getFrontHeight() * 0.5;
        for (int i = 0; i < 3; i++) {
            JTextField tf = new JTextField();
            tf.setBounds((int)layoutX, (int)(yBase + (btnHeight + 10) * (i + 1)), 40, 30);
            final int idx = i;
            tf.addActionListener(e -> {
                switch (idx) {
                    case 0 -> setAudioConfirmButton();
                    case 1 -> setColorConfirmButton();
                    case 2 -> setPositionConfirmButton();
                }
            });
            confirmButtonTextFields.add(tf);
        }
    }

    private static void setConfirmButtonInfoText() {
        String[] labels = {"Confirm Audio Match", "Confirm Color Match", "Confirm Position Match"};
        double yBase = CustomWindow2.getFrontHeight() * 0.5;
        for (int i = 0; i < 3; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setBounds((int)(layoutX + btnHeight + 20),
                          (int)(yBase + (btnHeight + 10) * (i + 1)), 300, 30);
            confirmInfoLabels.add(lbl);
        }
    }

    public static void setTextToConfirmButtonTextFields() {
        if (confirmButtonTextFields.size() >= 3) {
            confirmButtonTextFields.get(0).setText(TrainingScreen.confirmMatchAudio);
            confirmButtonTextFields.get(1).setText(TrainingScreen.confirmMatchColor);
            confirmButtonTextFields.get(2).setText(TrainingScreen.confirmMatchPosition);
        }
    }

    public static void setAudioConfirmButton() {
        String key = extractKey(confirmButtonTextFields.get(0).getText(), "L");
        TrainingScreen.confirmMatchAudio = key;
        confirmButtonTextFields.get(0).setText(key);
    }
    public static void setColorConfirmButton() {
        String key = extractKey(confirmButtonTextFields.get(1).getText(), "D");
        TrainingScreen.confirmMatchColor = key;
        confirmButtonTextFields.get(1).setText(key);
    }
    public static void setPositionConfirmButton() {
        String key = extractKey(confirmButtonTextFields.get(2).getText(), "S");
        TrainingScreen.confirmMatchPosition = key;
        confirmButtonTextFields.get(2).setText(key);
    }

    private static String extractKey(String input, String fallback) {
        String clean = input.replaceAll("[^a-zA-Z]", "");
        return clean.isEmpty() ? fallback : String.valueOf(clean.charAt(0)).toUpperCase();
    }

    public static void setOptionCheckBoxesSelected() {
        if (optionCheckBoxArray.size() >= 4) {
            optionCheckBoxArray.get(0).setSelected(DurationTraining.showProgressBarDurationTraining);
            optionCheckBoxArray.get(1).setSelected(DurationTraining.showProgressBarRegularTraining);
            optionCheckBoxArray.get(2).setSelected(DurationTraining.showRightAndFalseClicksInProgressBar);
            optionCheckBoxArray.get(3).setSelected(DurationTraining.showRightClicksImediately);
        }
    }
    public static void setCheckBoxesOptionsSelected()        { setOptionCheckBoxesSelected(); }
    public static void setCheckBoxesChooseAudioFileSelected() {
        for (int i = 0; i < numberOfRecordButtons; i++) {
            chooseAudioFileCheckBoxes.get(i).setSelected(chooseAudioFileBooleanArray[i]);
        }
    }

    public static void setRepetTimeToHighSpeed() {
        TimerClass.repeatTime = (TimerClass.repeatTime != 800) ? 800 : 10;
    }

    public static boolean switchBooleans(boolean v) { return !v; }

    public static void setOptionMenu() {
        var contentPane = drop.getContentPane();
        contentPane.getChildren().clear();
        for (JPanel p : audioBtnList)             contentPane.getChildren().add(p);
        for (JCheckBox cb : optionCheckBoxArray)  contentPane.getChildren().add(cb);
        for (JTextField tf : confirmButtonTextFields) contentPane.getChildren().add(tf);
        for (JLabel lbl : confirmInfoLabels)      contentPane.getChildren().add(lbl);
        for (JCheckBox cb : chooseAudioFileCheckBoxes) contentPane.getChildren().add(cb);

        if (!StartScreen.trainingStarted) {
            try { StartScreen.mainWindow.getFront().add(drop.newDropMenu()); } catch (Exception ignored) {}
        } else {
            try { TrainingScreen.root.add(drop.newDropMenu()); } catch (Exception ignored) {}
        }
    }
}
