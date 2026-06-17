package window;

import java.awt.Toolkit;

/**
 * Static dimension helper — replaces CustomWindow2 from CustomWindow.jar.
 * JavaFX code used CustomWindow2.getFrontWidth/Height() for layout calculations.
 */
public class CustomWindow2 {

    private static double frontWidth  = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static double frontHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    public static void setFrontSize(double w, double h) {
        frontWidth  = w;
        frontHeight = h;
    }

    public static double getFrontWidth()  { return frontWidth; }
    public static double getFrontHeight() { return frontHeight; }
}
