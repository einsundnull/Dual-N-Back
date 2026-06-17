package window;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import enums.TypeOfScrollPane;

/** Simplified Swing scroll pane for the user-select list. */
public class CustomScrollPane {

    private final JScrollPane scrollPane;
    private final JPanel      display;

    public CustomScrollPane(TypeOfScrollPane type, double width, double height) {
        display = new JPanel(null);
        display.setOpaque(true);
        display.setBackground(Color.WHITE);
        display.setPreferredSize(new Dimension((int)width, (int)height));
        scrollPane = new JScrollPane(display);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setSize((int)width, (int)height);
        scrollPane.setBounds(0, 0, (int)width, (int)height);
    }

    public void add(JPanel p)              { display.add(p); display.revalidate(); }
    public void setBarVerticalBarVisible(boolean b) {
        scrollPane.setVerticalScrollBarPolicy(b
            ? JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
            : JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    }
    public void setDisplayHeight(double h) {
        display.setPreferredSize(new Dimension(display.getWidth(), (int)h));
        display.revalidate();
    }

    /** Returns a ChildList proxy over the display panel. */
    public SwingPanel getDisplay() {
        return new SwingPanel() {
            @Override public ChildList getChildren() { return new ChildList(display); }
        };
    }

    public JScrollPane newScrollPane() { return scrollPane; }
}
