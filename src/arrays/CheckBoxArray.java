package arrays;

import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

/** Array of JCheckBoxes, added to a parent panel. */
public class CheckBoxArray {

    private final ArrayList<JCheckBox> list = new ArrayList<>();

    public CheckBoxArray(int count, JPanel parent,
                         double startX, double startY,
                         double width, double height,
                         double spacingX, double spacingY) {
        for (int i = 0; i < count; i++) {
            JCheckBox cb = new JCheckBox();
            cb.setBounds((int)(startX + i * spacingX),
                         (int)(startY + i * spacingY),
                         (int) width, (int) height);
            parent.add(cb);
            list.add(cb);
        }
    }

    public JCheckBox get(int i) { return list.get(i); }
    public int size()           { return list.size(); }
}
