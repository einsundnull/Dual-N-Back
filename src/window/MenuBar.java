package window;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

/** Custom title bar that replaces the OS window decoration. */
public class MenuBar extends JPanel {

    private final CustomButton exitBtn;
    private final CustomButton maximizeBtn;
    private final CustomButton minimizeBtn;
    private final JFrame frame;
    private Point dragStart;

    public MenuBar(JFrame frame, JPanel container) {
        this.frame = frame;
        setLayout(null);
        setBackground(new Color(50, 50, 50));
        setBounds(0, 0, 3000, 28);

        exitBtn     = new CustomButton(40, 22);
        maximizeBtn = new CustomButton(40, 22);
        minimizeBtn = new CustomButton(40, 22);

        exitBtn.setText("X");
        maximizeBtn.setText("□");
        minimizeBtn.setText("_");

        exitBtn.setLayout(0, 2);
        maximizeBtn.setLayout(44, 2);
        minimizeBtn.setLayout(88, 2);

        add(exitBtn.getFrame());
        add(maximizeBtn.getFrame());
        add(minimizeBtn.getFrame());

        maximizeBtn.setOnMouseClicked(() -> {
            if ((frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) != 0)
                frame.setExtendedState(JFrame.NORMAL);
            else
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        });

        minimizeBtn.setOnMouseClicked(() -> frame.setExtendedState(JFrame.ICONIFIED));

        // drag-to-move
        addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e)  { dragStart = e.getPoint(); }
            @Override public void mouseReleased(MouseEvent e) { dragStart = null; }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override public void mouseDragged(MouseEvent e) {
                if (dragStart != null) {
                    Point loc = frame.getLocation();
                    frame.setLocation(loc.x + e.getX() - dragStart.x,
                                      loc.y + e.getY() - dragStart.y);
                }
            }
        });
    }

    public void setExitAction(Runnable r) {
        exitBtn.setOnMouseClicked(r);
    }

    public CustomButton getExitButton()     { return exitBtn; }
    public CustomButton getMaximizeButton() { return maximizeBtn; }
    public CustomButton getMinimzeButton()  { return minimizeBtn; }  // original typo kept
}
