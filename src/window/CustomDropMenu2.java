package window;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import enums.TypeOfDropMenu;

/** Modern light slide-in panel for user selection. */
public class CustomDropMenu2 {

    private static final String FONT   = "Segoe UI";
    private static final Color BG      = Color.WHITE;
    private static final Color HEADER  = new Color(0xF4, 0xF6, 0xF9);
    private static final Color BORDER  = new Color(0xE2, 0xE5, 0xEA);
    private static final Color TEXT    = new Color(0x1E, 0x24, 0x30);
    private static final int   HEADER_H = 52;

    private final JPanel  containerPane;   // animated slider (added to the overlay)
    private final JPanel  contentPane;     // list content, below the header
    private final JPanel  header;
    private final JLabel  titleLbl;
    private final JButton closeBtn;

    private int menuW = 320, menuH = 600;
    private boolean playing = false;
    private boolean showCloseWhenVisible = true;
    private boolean showCloseWhenHidden  = false;
    private Runnable closeAction;
    private Timer anim;

    public CustomDropMenu2(TypeOfDropMenu dir, boolean autoSize) {
        containerPane = new JPanel(null);
        containerPane.setVisible(false);
        containerPane.setOpaque(true);
        containerPane.setBackground(BG);
        containerPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER));

        header = new JPanel(null);
        header.setOpaque(true);
        header.setBackground(HEADER);
        containerPane.add(header);

        titleLbl = new JLabel("Select User");
        titleLbl.setForeground(TEXT);
        titleLbl.setFont(new Font(FONT, Font.BOLD, 16));
        header.add(titleLbl);

        closeBtn = new JButton("✕");
        closeBtn.setFont(new Font(FONT, Font.BOLD, 16));
        closeBtn.setForeground(TEXT);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> { if (closeAction != null) closeAction.run(); });
        header.add(closeBtn);

        contentPane = new JPanel(null);
        contentPane.setOpaque(true);
        contentPane.setBackground(BG);
        containerPane.add(contentPane);

        relayout();
    }

    private void relayout() {
        containerPane.setSize(menuW, menuH);
        header.setBounds(0, 0, menuW, HEADER_H);
        titleLbl.setBounds(18, 0, menuW - 70, HEADER_H);
        closeBtn.setBounds(menuW - 46, 10, 32, 32);
        contentPane.setBounds(0, HEADER_H, menuW, menuH - HEADER_H);
        for (Component c : contentPane.getComponents())
            c.setBounds(0, 0, menuW, menuH - HEADER_H);
    }

    public void play() {
        playing = true;
        closeBtn.setVisible(showCloseWhenVisible);
        containerPane.setVisible(true);
        slide(true);
    }

    public void back() {
        playing = false;
        slide(false);
    }

    private void slide(boolean in) {
        if (anim != null && anim.isRunning()) anim.stop();
        final int target = in ? 0 : -menuW;
        final int step   = Math.max(14, menuW / 16);
        if (in) containerPane.setLocation(-menuW, containerPane.getY());
        anim = new Timer(8, null);
        anim.addActionListener(e -> {
            int x = containerPane.getX();
            x = in ? Math.min(target, x + step) : Math.max(target, x - step);
            containerPane.setLocation(x, containerPane.getY());
            if (containerPane.getParent() != null) containerPane.getParent().repaint();
            if (x == target) {
                anim.stop();
                if (!in) {
                    containerPane.setVisible(false);
                    closeBtn.setVisible(showCloseWhenHidden);
                }
            }
        });
        anim.start();
    }

    public void playAndBack() { if (playing) back(); else play(); }
    public boolean isPlay()   { return playing; }

    public void disable(boolean d) {
        for (Component c : contentPane.getComponents()) c.setEnabled(!d);
    }

    /** Content (the scroll pane) fills the area below the header. */
    public void add(Component p) {
        p.setBounds(0, 0, menuW, menuH - HEADER_H);
        contentPane.add(p);
    }

    public void setPrefSize(double w, double h) {
        menuW = (int) w; menuH = (int) h;
        relayout();
        containerPane.setBounds(containerPane.getX(), containerPane.getY(), menuW, menuH);
    }

    public void setLayoutY(double y) {
        containerPane.setLocation(containerPane.getX(), (int) y);
    }

    public void setTitle(String t) { titleLbl.setText(t); }

    public void showCloseArrowAfterClosing(boolean b)         { showCloseWhenHidden  = b; }
    public void showCloseArrowWhenDropMenuIsVisible(boolean b) { showCloseWhenVisible = b; }
    public void setDuration(Object d)                         {}
    public void closeArrowSetOnAction(Runnable r)             { closeAction = r; }

    public JPanel newDropMenu() { return containerPane; }
}
