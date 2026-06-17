package arrays;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Creates a vertical list of JTextFields.
 *
 * Parameters: count, parent, startX, startY, font, width, height, spacingX, spacingY
 */
public class TextFieldArray {

    private final ArrayList<JTextField> list = new ArrayList<>();

    public TextFieldArray(int count, JPanel parent,
                          double startX, double startY,
                          Font font,
                          double width, double height,
                          double spacingX, double spacingY) {
        for (int i = 0; i < count; i++) {
            JTextField tf = new JTextField();
            tf.setBounds((int)(startX + i * spacingX),
                         (int)(startY + i * spacingY),
                         (int) width, (int) height);
            if (font != null) tf.setFont(font);
            parent.add(tf);
            list.add(tf);
        }
    }

    public JTextField get(int i) { return list.get(i); }
    public int size()            { return list.size(); }
}
