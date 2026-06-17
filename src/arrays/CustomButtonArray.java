package arrays;

import java.util.ArrayList;

import javax.swing.JPanel;

import window.CustomButton;

/**
 * Creates a vertical list of CustomButtons and adds them to a parent panel.
 *
 * Parameters mirror the original CustomWindow.jar constructor:
 *   count, parent, startX, startY, width, height, spacingX, spacingY
 */
public class CustomButtonArray {

    private final ArrayList<CustomButton> list = new ArrayList<>();

    public CustomButtonArray(int count, JPanel parent,
                             double startX, double startY,
                             double width,  double height,
                             double spacingX, double spacingY) {
        for (int i = 0; i < count; i++) {
            CustomButton btn = new CustomButton(width, height);
            btn.setLayout(startX + i * spacingX, startY + i * spacingY);
            parent.add(btn.getFrame());
            list.add(btn);
        }
    }

    public CustomButton get(int i) { return list.get(i); }
    public int size()              { return list.size(); }

    public void setFont(String family, double size) {
        list.forEach(b -> b.setFont(family, size));
    }
}
