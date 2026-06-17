package screens;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import enums.TypeOfWindow;
import language.LanguageClass;
import window.CustomButton;
import window.CustomWindow;
import window.SwingPanel;

public class ResultScreen {

    public static double  height = StartScreen.screenHeight;
    private CustomButton  exit;
    public static SwingPanel results = new SwingPanel();
    public static SwingPanel menu    = new SwingPanel();
    private static CustomWindow resultScreen;
    private static double width = StartScreen.screenWidth;

    public ResultScreen() {
        resultScreen = new CustomWindow(TypeOfWindow.SECONDARYSTAGE, width, height);
        resultScreen.setFullScreen(true);
        resultScreen.setResizeable(false);

        SetResultsCurves.scanForMaxNBackPositionAndColor();
        SetResultsCurves.setLevelLines();

        setModeLabel();

        results.setBounds(0, 0, (int) width, (int) height);
        menu.setBounds(0, (int)(height - height * 0.10), (int) width, (int)(height * 0.10));
        menu.setStyle("-fx-background-color: lightgray");

        String hint = LanguageClass.word(StartScreen.indexLang, 41);
        JLabel hintLabel = new JLabel(hint);
        hintLabel.setBounds((int)(width / 2 - StartScreen.screenWidth * 0.08125),
                            (int)(StartScreen.screenWidth * 0.00625),
                            300, 24);
        hintLabel.setFont(new Font("Arial", Font.PLAIN, (int)(StartScreen.screenWidth * 0.01125)));
        menu.add(hintLabel);

        resultScreen.add(results);
        resultScreen.add(menu);

        exit = new CustomButton(StartScreen.screenWidth * 0.02625, StartScreen.screenWidth * 0.0125);
        exit.setText("X");
        exit.setLayout(width - StartScreen.screenWidth * 0.04125, StartScreen.screenWidth * 0.009375);
        exit.setStyle("-fx-background-color: darkred");
        exit.setOnMouseClicked(() -> { resultScreen.close(); resetValues(); });
        resultScreen.setExitButtonOnAction(() -> { resultScreen.close(); resetValues(); });
        resultScreen.add(exit.getFrame());

        CheckBoxMenuFromResultsFile cbMenu = new CheckBoxMenuFromResultsFile(StartScreen.fileAddition);
        cbMenu.getPane().setStyle("-fx-background-color: lightgray");
        menu.add(cbMenu.getPane());

        resultScreen.show();
    }

    public ResultScreen(String empty) {}

    private void setModeLabel() {
        JLabel lbl = new JLabel("", SwingConstants.CENTER);
        lbl.setBounds((int)(width * 0.1), (int)(StartScreen.screenWidth * 0.01875),
                      (int)(width * 0.8), (int)(StartScreen.screenWidth * 0.04));
        lbl.setFont(new Font("Arial", Font.PLAIN, (int)(StartScreen.screenWidth * 0.01875)));
        results.add(lbl);
    }

    public static void resetValues() {
        menu.removeAll();
        results.removeAll();
        SetResultsCurves.maxNBback = 0;
    }
}
