package window;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JPanel;

import enums.TypeOfDropMenu;

/**
 * Swing replacement for animated CustomDropMenu from CustomWindow.jar.
 * Slide animation replaced with simple show/hide.
 */
public class CustomDropMenu {

    private final JPanel containerPane;
    private final JPanel contentPane;
    private boolean playing = false;

    public CustomDropMenu() {
        this(null, false);
    }

    public CustomDropMenu(TypeOfDropMenu dir, boolean autoSize) {
        containerPane = new JPanel(null);
        containerPane.setVisible(false);

        contentPane = new JPanel(null);
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBounds(0, 0, 600, 400);
        containerPane.add(contentPane);
        containerPane.setSize(600, 400);
    }

    public void playAndBack() {
        playing = !playing;
        containerPane.setVisible(playing);
        containerPane.repaint();
    }

    public void play() {
        playing = true;
        containerPane.setVisible(true);
    }

    public void back() {
        playing = false;
        containerPane.setVisible(false);
    }

    public boolean isPlay() { return playing; }

    public void disable(boolean d) {
        for (Component c : contentPane.getComponents()) c.setEnabled(!d);
    }

    // --- Layout / size ---
    public void setPrefSize(double w, double h) {
        contentPane.setSize((int)w,(int)h);
        containerPane.setSize((int)w,(int)h);
    }
    public void setLayoutX(double x) { containerPane.setLocation((int)x, containerPane.getY()); }
    public void setLayoutY(double y) { containerPane.setLocation(containerPane.getX(), (int)y); }

    // --- Config stubs ---
    public void showCloseArrowAfterClosing(boolean b)      {}
    public void showCloseArrowWhenDropMenuIsVisible(boolean b) {}
    public void closeArrowSetOnAction(Runnable r)          {}

    // --- Content access ---
    public SwingPanel getContentPane() {
        // Return a SwingPanel proxy so callers can use setStyle/getChildren()
        return new SwingPanel() {
            @Override public void setStyle(String s) {
                Color c = StyleUtil.parseColor(s);
                if (c != null) { contentPane.setBackground(c); contentPane.setOpaque(true); }
            }
            @Override public void setOnMouseClicked(Runnable r) {
                contentPane.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override public void mouseClicked(java.awt.event.MouseEvent e) { r.run(); }
                });
            }
            @Override public ChildList getChildren() {
                return new ChildList(contentPane);
            }
        };
    }

    /** Returns the JPanel to add to the parent window. */
    public JPanel newDropMenu() { return containerPane; }

    public SwingPanel getContainerPane() {
        SwingPanel proxy = new SwingPanel() {
            @Override public void toFront() {
                Container p = containerPane.getParent();
                if (p != null) { p.setComponentZOrder(containerPane,0); p.repaint(); }
            }
        };
        return proxy;
    }
}
