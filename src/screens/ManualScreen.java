package screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import enums.TypeOfWindow;
import language.LanguageClass;
import window.CustomButton;
import window.CustomWindow;
import window.SwingPanel;

/** Shows the help/manual images. */
public class ManualScreen {

    private final CustomWindow manual;

    public ManualScreen() {
        double w = StartScreen.screenWidth;
        double h = StartScreen.screenHeight;
        manual = new CustomWindow(TypeOfWindow.SECONDARYSTAGE, w, h);

        SwingPanel root = manual.getFront();
        root.setStyle("-fx-background-color: black");

        // Manual image for current language
        String langCode = LanguageClass.word(StartScreen.indexLang, 28);
        URL url = getClass().getResource("/manual/" + langCode + ".png");
        if (url == null) url = getClass().getResource("/manual/en.png");

        if (url != null) {
            ImageIcon icon = new ImageIcon(
                new ImageIcon(url).getImage().getScaledInstance((int)w, (int)(h * 0.9), Image.SCALE_SMOOTH));
            JLabel img = new JLabel(icon);
            img.setBounds(0, 0, (int)w, (int)(h * 0.9));
            root.add(img);
        } else {
            JLabel placeholder = new JLabel(LanguageClass.word(StartScreen.indexLang, 31), SwingConstants.CENTER);
            placeholder.setForeground(Color.WHITE);
            placeholder.setFont(new Font("Arial", Font.PLAIN, 24));
            placeholder.setBounds(0, 0, (int)w, (int)h);
            root.add(placeholder);
        }

        CustomButton closeBtn = new CustomButton(w * 0.1, w * 0.025);
        closeBtn.setText("Close");
        closeBtn.setLayout(w * 0.45, h * 0.93);
        closeBtn.setOnMouseClicked(() -> manual.close());
        root.add(closeBtn.getFrame());

        manual.show();
    }
}
