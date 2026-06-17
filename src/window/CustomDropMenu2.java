package window;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;

import enums.TypeOfDropMenu;

/** Swing slide-in panel for user selection. */
public class CustomDropMenu2 {

    private final JPanel containerPane;
    private final JPanel contentPane;
    private final JButton arrowBtn;
    private boolean playing = false;
    private boolean showArrowWhenVisible = true;
    private boolean showArrowWhenHidden  = false;
    private Runnable closeAction;

    public CustomDropMenu2(TypeOfDropMenu dir, boolean autoSize) {
        containerPane = new JPanel(null);
        containerPane.setVisible(false);
        containerPane.setBackground(new Color(45, 45, 45));

        contentPane = new JPanel(null);
        contentPane.setBackground(new Color(60, 60, 60));
        contentPane.setBounds(0, 0, 300, 600);
        containerPane.add(contentPane);
        containerPane.setSize(300, 600);

        // Arrow/close button at top-right of the content pane
        arrowBtn = new JButton("<");
        arrowBtn.setFont(new Font("Arial", Font.BOLD, 14));
        arrowBtn.setBackground(new Color(80, 80, 80));
        arrowBtn.setForeground(Color.WHITE);
        arrowBtn.setBorderPainted(false);
        arrowBtn.setFocusPainted(false);
        arrowBtn.setBounds(0, 0, 30, 600);
        arrowBtn.addActionListener(e -> {
            if (closeAction != null) closeAction.run();
        });
        containerPane.add(arrowBtn);
    }

    public void play() {
        playing = true;
        containerPane.setVisible(true);
        arrowBtn.setText("<");
        arrowBtn.setVisible(showArrowWhenVisible);
        containerPane.repaint();
        if (containerPane.getParent() != null) {
            containerPane.getParent().repaint();
        }
    }

    public void back() {
        playing = false;
        containerPane.setVisible(false);
        arrowBtn.setVisible(showArrowWhenHidden);
        if (containerPane.getParent() != null) {
            containerPane.getParent().repaint();
        }
    }

    public void playAndBack() { if (playing) back(); else play(); }
    public boolean isPlay()   { return playing; }

    public void disable(boolean d) {
        for (Component c : contentPane.getComponents()) c.setEnabled(!d);
    }

    public void add(Component p) {
        // position the content component next to the arrow button
        int arrowW = 30;
        int cw = contentPane.getWidth();
        int ch = contentPane.getHeight();
        p.setBounds(arrowW, 0, cw - arrowW, ch);
        contentPane.add(p);
    }

    public void setPrefSize(double w, double h) {
        contentPane.setSize((int)w, (int)h);
        contentPane.setBounds(0, 0, (int)w, (int)h);
        containerPane.setSize((int)w, (int)h);
        containerPane.setBounds(0, 0, (int)w, (int)h);
        arrowBtn.setBounds(0, 0, 30, (int)h);
    }

    public void setLayoutY(double y) {
        containerPane.setLocation(containerPane.getX(), (int)y);
    }

    public void showCloseArrowAfterClosing(boolean b)          { showArrowWhenHidden = b; }
    public void showCloseArrowWhenDropMenuIsVisible(boolean b)  { showArrowWhenVisible = b; }
    public void setDuration(Object d)                          {}
    public void closeArrowSetOnAction(Runnable r)              { closeAction = r; }

    public JPanel newDropMenu() { return containerPane; }
}
