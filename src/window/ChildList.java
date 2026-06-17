package window;

import java.awt.Component;

import javax.swing.JPanel;

/** Mimics JavaFX ObservableList returned by Pane.getChildren(). */
public class ChildList {

    private final JPanel panel;

    public ChildList(JPanel panel) {
        this.panel = panel;
    }

    public void add(Component c) {
        panel.add(c);
        panel.revalidate();
        panel.repaint();
    }

    public void addAll(Component... cs) {
        for (Component c : cs) panel.add(c);
        panel.revalidate();
        panel.repaint();
    }

    public void clear() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }
}
