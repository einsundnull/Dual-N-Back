package window;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



/** Swing replacement for the CustomButton from CustomWindow.jar. */
public class CustomButton {

    private final JPanel  frame;
    private final JButton btn;
    private boolean disabled = false;
    private Runnable clickAction;
    private String   id = "";
    private int bx, by, bw, bh;

    // --- Flat (modern) styling state ---
    private boolean flat = false;
    private Color flatBg, flatFg, flatHover;
    private Color flatDisabledBg = new Color(0xE6, 0xE8, 0xEC);
    private Color flatDisabledFg = new Color(0xA0, 0xA4, 0xAA);

    public CustomButton(double width, double height) {
        bw = (int) width;
        bh = (int) height;
        frame = new JPanel(null);
        frame.setOpaque(false);
        frame.setBounds(0, 0, bw, bh);

        btn = new JButton();
        btn.setBounds(0, 0, bw, bh);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        frame.add(btn);

        btn.addActionListener(e -> {
            if (!disabled && clickAction != null) clickAction.run();
        });
    }

    // --- Layout ---
    public void setLayout(double x, double y) {
        bx = (int) x; by = (int) y;
        frame.setBounds(bx, by, bw, bh);
    }
    public void setLayoutX(double x) { bx = (int)x; frame.setBounds(bx,by,bw,bh); }
    public void setLayoutY(double y) { by = (int)y; frame.setBounds(bx,by,bw,bh); }
    public double getLayoutX() { return bx; }
    public double getLayoutY() { return by; }

    // --- Text / font ---
    public void setText(String text) { btn.setText(text); }
    public String getText()          { return btn.getText(); }
    public void setFont(String family, double size) {
        btn.setFont(new Font(family.isEmpty() ? "Arial" : family, Font.PLAIN, (int)size));
    }

    // --- Visibility ---
    public void setVisible(boolean v) { frame.setVisible(v); btn.setVisible(v); }
    public boolean isVisible()        { return frame.isVisible(); }

    // --- Enable ---
    public void setDisable(boolean d) { disabled = d; btn.setEnabled(!d); applyFlatColors(); }
    public boolean isDisable()        { return disabled; }

    // --- Flat modern styling ---
    public void setFlat(Color bg, Color fg, Color hover) {
        flat = true; flatBg = bg; flatFg = fg; flatHover = hover;
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        applyFlatColors();
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { if (!disabled) btn.setBackground(flatHover); }
            @Override public void mouseExited(MouseEvent e)  { if (!disabled) btn.setBackground(flatBg); }
        });
    }
    private void applyFlatColors() {
        if (!flat) return;
        if (disabled) { btn.setBackground(flatDisabledBg); btn.setForeground(flatDisabledFg); }
        else          { btn.setBackground(flatBg);         btn.setForeground(flatFg); }
    }
    public void setTextAlignLeft() {
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 12));
    }
    public void setFontStyle(String family, int style, int size) {
        btn.setFont(new Font(family.isEmpty() ? "Segoe UI" : family, style, size));
    }
    public void setBoundsDirect(int x, int y, int w, int h) {
        bx = x; by = y; bw = w; bh = h;
        frame.setBounds(x, y, w, h);
        btn.setBounds(0, 0, w, h);
    }

    // --- ID (for SelectUser) ---
    public void setID(String id) { this.id = id; }
    public String getID()        { return id; }

    // --- Style ---
    public void setShadowColor(String color) {
        Color c = StyleUtil.parseColor("-fx-background-color:" + color);
        if (c != null) btn.setForeground(c);
    }
    public void setStyle(String fxStyle) {
        Color c = StyleUtil.parseColor(fxStyle);
        if (c != null) { btn.setBackground(c); btn.setOpaque(true); }
        else { btn.setBackground(null); btn.setOpaque(false); }
        btn.repaint();
    }

    // --- Action ---
    public void setOnMouseClicked(Runnable r) { clickAction = r; }

    // --- Label accessor (used by legacy CreateUser/SelectUser) ---
    public CustomButton getLabel() { return this; }

    // --- Frame accessors ---
    public JPanel getFrame()  { return frame; }
    public JPanel getFront()  { return frame; }
    public JPanel newButton() { return frame; }
}
