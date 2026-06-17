package screens;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JLabel;

import files.ResultsFiles;
import window.SwingPanel;

public class SetResultsCurves {

    private static int tempNBack;
    public static  int maxNBback;

    public static void scanForMaxNBackPositionAndColor() {
        String path = StartScreen.durationTraining
            ? ResultsFiles.storingPathDuration
            : ResultsFiles.storingPathRegular;
        File file = new File(path);
        if (!file.exists()) return;
        try (Scanner scn = new Scanner(file)) {
            while (scn.hasNext()) {
                String tok = scn.next();
                if ("n-back:".equals(tok) && scn.hasNext()) {
                    int n = (int) Double.parseDouble(scn.next());
                    if (maxNBback < n) maxNBback = n;
                }
            }
        } catch (FileNotFoundException e) { e.printStackTrace(); }
    }

    public static void setLevelLines() {
        if (maxNBback <= 0) return;
        double firstY   = StartScreen.screenHeight - StartScreen.screenWidth * 0.05625;
        double distance = firstY / (maxNBback + 2);

        for (int i = 0; i <= maxNBback + 2; i++) {
            SwingPanel rule = new SwingPanel();
            rule.setBounds(0, (int)(firstY - distance * i), (int) StartScreen.screenWidth, 1);
            rule.setStyle("-fx-background-color: lightgray");
            ResultScreen.results.add(rule);

            JLabel lbl = new JLabel(String.valueOf(i));
            lbl.setBounds(2, (int)(firstY - distance * i - 10),
                          (int)(StartScreen.screenWidth * 0.025),
                          (int)(StartScreen.screenWidth * 0.02));
            lbl.setFont(new Font("Arial", Font.PLAIN, (int)(StartScreen.screenWidth * 0.01125)));
            ResultScreen.results.add(lbl);
        }
        drawSessionDots(firstY, distance);
    }

    private static void drawSessionDots(double firstY, double distance) {
        String path = StartScreen.durationTraining
            ? ResultsFiles.storingPathDuration
            : ResultsFiles.storingPathRegular;
        File file = new File(path);
        if (!file.exists()) return;

        ArrayList<Integer> nbacks = new ArrayList<>();
        try (Scanner scn = new Scanner(file)) {
            while (scn.hasNext()) {
                String tok = scn.next();
                if ("n-back:".equals(tok) && scn.hasNext())
                    nbacks.add((int) Double.parseDouble(scn.next()));
            }
        } catch (FileNotFoundException e) { e.printStackTrace(); }

        if (nbacks.isEmpty()) return;
        double stepX = StartScreen.screenWidth / (nbacks.size() + 1.0);
        int prevX = -1, prevY = -1;

        for (int i = 0; i < nbacks.size(); i++) {
            int x = (int)((i + 1) * stepX);
            int y = (int)(firstY - distance * nbacks.get(i));

            SwingPanel dot = new SwingPanel();
            dot.setBounds(x - 3, y - 3, 6, 6);
            dot.setStyle("-fx-background-color: blue");
            ResultScreen.results.add(dot);

            if (prevX >= 0) {
                SwingPanel hLine = new SwingPanel();
                hLine.setBounds(Math.min(prevX,x), Math.min(prevY,y), Math.abs(x-prevX)+1, 1);
                hLine.setStyle("-fx-background-color: blue");
                ResultScreen.results.add(hLine);

                SwingPanel vLine = new SwingPanel();
                vLine.setBounds(x, Math.min(prevY,y), 1, Math.abs(y-prevY)+1);
                vLine.setStyle("-fx-background-color: blue");
                ResultScreen.results.add(vLine);
            }
            prevX = x; prevY = y;
        }
    }
}
