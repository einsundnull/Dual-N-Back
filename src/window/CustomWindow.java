package window;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import enums.TypeOfWindow;

/**
 * Swing replacement for CustomWindow from CustomWindow.jar.
 * Wraps a JFrame with a custom title bar (MenuBar) and a content panel (front).
 */
public class CustomWindow {

    private final JFrame    jframe;
    private final SwingPanel outerPanel;   // getFrame()
    private final SwingPanel front;        // getFront()
    private final MenuBar   menuBar;       // kept for API compatibility; no longer shown
    private Runnable        exitAction;
    private boolean         fullscreen;
    private static final Cursor BLANK_CURSOR;

    static {
        BLANK_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(
            new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
            new Point(), "blank");
    }

    public CustomWindow(TypeOfWindow type, double width, double height) {
        jframe = new JFrame("N-Back");
        // Native OS window decoration (real title bar, min/max/close).
        jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        outerPanel = new SwingPanel();
        outerPanel.setStyle("-fx-background-color: white");
        outerPanel.setLayout(null);

        // MenuBar is no longer added to the window (OS draws the title bar);
        // kept instantiated so getMenuBar()/shadow-color calls remain valid.
        menuBar = new MenuBar(jframe, outerPanel);

        front = new SwingPanel();
        front.setLayout(null);
        front.setStyle("-fx-background-color: lightgrey");

        jframe.setContentPane(outerPanel);
        outerPanel.add(front);

        jframe.setSize((int) width, (int) height);
        front.setBounds(0, 0, (int) width, (int) height);
        CustomWindow2.setFrontSize((int) width, (int) height);

        jframe.addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) { layoutPanels(); }
            @Override public void componentShown(ComponentEvent e)   { layoutPanels(); }
        });

        // Native close button routes through the app's exit logic.
        jframe.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                if (exitAction != null) exitAction.run();
                else jframe.dispose();
            }
        });
    }

    private void layoutPanels() {
        int w = outerPanel.getWidth();
        int h = outerPanel.getHeight();
        front.setBounds(0, 0, w, h);
        CustomWindow2.setFrontSize(w, h);
    }

    // --- JavaFX API compatibility ---

    public SwingPanel getFront()    { return front; }
    public SwingPanel getFrame()    { return outerPanel; }
    public JFrame     getScene()    { return jframe; }       // used for key/mouse listeners
    public JFrame     getPrimaryStage() { return jframe; }

    public MenuBar getMenuBar()     { return menuBar; }

    public void add(Component c) {
        front.add(c);
        front.setComponentZOrder(c, 0);
        front.revalidate();
        front.repaint();
    }

    public void show() {
        if (fullscreen) {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            if (gd.isFullScreenSupported()) {
                gd.setFullScreenWindow(jframe);   // true borderless fullscreen (covers taskbar)
                layoutPanels();
                front.revalidate();
                front.repaint();
                return;
            }
            jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);  // fallback
        }
        jframe.setVisible(true);
    }

    public void close() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.getFullScreenWindow() == jframe) gd.setFullScreenWindow(null);
        jframe.dispose();
    }

    public void setMaximized()      { jframe.setExtendedState(JFrame.MAXIMIZED_BOTH); }

    /** Real fullscreen: undecorate now (frame is not yet shown) and switch the device on show(). */
    public void setFullScreen(boolean b) {
        fullscreen = b;
        if (b) {
            if (jframe.isDisplayable()) jframe.dispose();
            jframe.setUndecorated(true);
        }
    }

    public void hideCursor(boolean hide) {
        jframe.setCursor(hide ? BLANK_CURSOR : Cursor.getDefaultCursor());
    }

    public void setResizeable(boolean b) { jframe.setResizable(b); }

    public void setOnMouseDragged(boolean b) {}

    public void setStyle(String fxStyle) {
        Color c = StyleUtil.parseColor(fxStyle);
        if (c != null) { outerPanel.setOpaque(true); outerPanel.setBackground(c); }
    }

    /** Overlay panels (drop menus) are always kept enabled so they stay interactive. */
    private static final String OVERLAY_MARKER = "overlay";

    public void disable(boolean disable) {
        front.setEnabled(!disable);
        for (Component comp : front.getComponents()) {
            // skip overlay panels (SelectUser drop menu, OptionMenu, etc.)
            if (OVERLAY_MARKER.equals(comp.getName())) continue;
            comp.setEnabled(!disable);
        }
    }

    public void markAsOverlay(Component comp) {
        comp.setName(OVERLAY_MARKER);
    }

    public void onlyAllowToSendToTray(boolean b) {}

    public void setExitButtonOnAction(Runnable r) {
        exitAction = r;
        menuBar.setExitAction(r);
    }

    public void changeListener(Runnable r) {
        jframe.addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) { r.run(); }
        });
    }

    // --- Static helpers (used by SelectUser) ---
    public static double getFrontWidth()  { return CustomWindow2.getFrontWidth(); }
    public static double getFrontHeight() { return CustomWindow2.getFrontHeight(); }

    // --- Overlay layer (used by SelectUser) ---
    public SwingPanel getOverLayer() { return front; }
}
