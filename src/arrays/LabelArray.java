package arrays;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JPanel;

import window.SwingLabel;

/**
 * Creates a vertical list of SwingLabels (replaces javafx.scene.control.Label).
 *
 * Parameters: count, parent, startX, startY, font, width, height, spacingX, spacingY
 */
public class LabelArray {

    private final ArrayList<SwingLabel> list = new ArrayList<>();

    public LabelArray(int count, JPanel parent,
                      double startX, double startY,
                      Font font,
                      double width, double height,
                      double spacingX, double spacingY) {
        for (int i = 0; i < count; i++) {
            SwingLabel lbl = new SwingLabel();
            lbl.setBounds((int)(startX + i * spacingX),
                          (int)(startY + i * spacingY),
                          (int) width, (int) height);
            if (font != null) lbl.setFont(font);
            parent.add(lbl);
            list.add(lbl);
        }
    }

    public SwingLabel get(int i) { return list.get(i); }
    public int size()            { return list.size(); }
}
