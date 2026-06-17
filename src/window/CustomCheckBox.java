package window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Swing replacement for CustomCheckBox from CustomWindow.jar. */
public class CustomCheckBox {

    private final JPanel container;
    private final JCheckBox box;
    private final JLabel textLabel;
    private int bx, by;
    private int bw, bh;

    public CustomCheckBox() {
        container = new JPanel(null);
        container.setOpaque(false);

        box = new JCheckBox();
        box.setOpaque(false);
        box.setBounds(0, 0, 20, 20);

        textLabel = new JLabel();
        textLabel.setBounds(24, 0, 300, 20);

        container.add(box);
        container.add(textLabel);
    }

    public void setPrefSize(double w, double h) {
        bw = (int) w; bh = (int) h;
        box.setBounds(0, 0, (int) w, (int) h);
        textLabel.setBounds((int) w + 4, 0, 300, (int) h);
        applyBounds();
    }

    public void setLayoutX(double x) { bx = (int) x; applyBounds(); }
    public void setLayoutY(double y) { by = (int) y; applyBounds(); }

    private void applyBounds() {
        container.setBounds(bx, by, bw + 304, bh);
    }

    public void setSelected(boolean sel) { box.setSelected(sel); }
    public boolean isSelected()          { return box.isSelected(); }

    public void setText(String text)     { textLabel.setText(text); }
    public void setTextWidth(int w)      { textLabel.setPreferredSize(new Dimension(w, textLabel.getHeight())); }

    // --- Theme styling ---
    public void setTextColor(Color c)    { textLabel.setForeground(c); }
    public void setTextFont(Font f)      { textLabel.setFont(f); }

    public void setOnAction(Runnable r) {
        box.addActionListener(e -> r.run());
    }

    /** Returns the container panel to add to a parent. */
    public JPanel newCheckBox() { return container; }
}
