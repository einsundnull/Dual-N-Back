package arrays;

import java.util.ArrayList;

import javax.swing.JPanel;

import window.SwingPanel;

/**
 * Creates an array of SwingPanels (replaces javafx.scene.layout.Pane arrays).
 *
 * Parameters: count, parent, startX, startY, font(ignored),
 *             width, height, spacingX, spacingY
 * Overload with boolean (horizontal=false → vertical layout).
 */
public class PaneArray {

    private final ArrayList<SwingPanel> list = new ArrayList<>();

    public PaneArray(int count, JPanel parent,
                     double startX, double startY,
                     Object font,
                     double width, double height,
                     double spacingX, double spacingY) {
        this(count, parent, startX, startY, font, width, height, spacingX, spacingY, true);
    }

    public PaneArray(int count, JPanel parent,
                     double startX, double startY,
                     Object font,
                     double width, double height,
                     double spacingX, double spacingY,
                     boolean horizontal) {
        for (int i = 0; i < count; i++) {
            SwingPanel p = new SwingPanel();
            int x = (int)(startX + (horizontal ? i * spacingX : 0));
            int y = (int)(startY + (horizontal ? 0 : i * spacingY));
            // If width/height are 0 we still create the panel; bounds set later via setLayoutX/Y
            p.setBounds(x, y, (int) width, (int) height);
            parent.add(p);
            list.add(p);
        }
    }

    public SwingPanel get(int i) { return list.get(i); }
    public int size()            { return list.size(); }
}
