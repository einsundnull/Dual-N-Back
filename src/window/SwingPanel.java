package window;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * Drop-in replacement for javafx.scene.layout.Pane.
 * Uses null layout so setLayoutX/Y + setPrefSize map to setBounds().
 */
public class SwingPanel extends JPanel {

    private int bx, by, bw, bh;
    private String storedId = "0";

    public SwingPanel() {
        super(null);
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

    // --- JavaFX Node.getChildren() compatibility ---
    public ChildList getChildren() { return new ChildList(this); }

    // --- JavaFX opacity ---
    public void setOpacity(double v) {
        // Swing has no built-in component opacity; handled via custom paint
        putClientProperty("opacity", v);
        repaint();
    }

    // --- JavaFX id (used by DurationTraining for progress bar state) ---
    public void setId(String id) { storedId = id; }
    public String getId()        { return storedId; }

    // --- Mouse transparency (JavaFX compat) ---
    public void setMouseTransparent(boolean transparent) {
        setEnabled(!transparent);
        for (java.awt.Component c : getComponents()) c.setEnabled(!transparent);
    }

    // --- Mouse click shortcut ---
    public void setOnMouseClicked(Runnable r) {
        addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { r.run(); }
        });
    }

    // --- toFront() ---
    public void toFront() {
        Container parent = getParent();
        if (parent != null) {
            parent.setComponentZOrder(this, 0);
            parent.repaint();
        }
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
