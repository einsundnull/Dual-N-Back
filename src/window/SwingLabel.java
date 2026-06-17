package window;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Drop-in replacement for javafx.scene.control.Label.
 * Adds JavaFX-style positioning and styling helpers.
 */
public class SwingLabel extends JLabel {

    private int bx, by, bw, bh;

    public SwingLabel() {
        super();
        setOpaque(false);
    }

    public SwingLabel(String text) {
        super(text);
        setOpaque(false);
    }

    // --- JavaFX-like positioning ---
    public void setLayoutX(double x) { bx = (int) x; applyBounds(); }
    public void setLayoutY(double y) { by = (int) y; applyBounds(); }
    public void setPrefSize(double w, double h) { bw = (int) w; bh = (int) h; applyBounds(); }
    public void setPrefWidth(double w)  { bw = (int) w; applyBounds(); }
    public void setPrefHeight(double h) { bh = (int) h; applyBounds(); }

    private void applyBounds() {
        setBounds(bx, by, bw, bh);
    }

    // --- JavaFX-like styling ---
    public void setStyle(String fxStyle) {
        Color c = StyleUtil.parseColor(fxStyle);
        if (c != null) { setOpaque(true); setBackground(c); }
        else            { setOpaque(false); }
        repaint();
    }

    // --- setTextFill replaces JavaFX setTextFill ---
    public void setTextFill(Color color) {
        setForeground(color);
    }

    // --- JavaFX Pos.CENTER alignment ---
    public void setAlignment(Object pos) {
        // pos is a string or Pos enum; we just center
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }

    // --- JavaFX Font.font("name", size) ---
    // Call setFont(new Font("name", Font.PLAIN, size)) directly instead.

    // --- opacity ---
    public void setOpacity(double v) {
        putClientProperty("opacity", v);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Object op = getClientProperty("opacity");
        if (op instanceof Double d && d < 1.0) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, d.floatValue()));
            super.paintComponent(g2);
            g2.dispose();
        } else {
            super.paintComponent(g);
        }
    }
}
