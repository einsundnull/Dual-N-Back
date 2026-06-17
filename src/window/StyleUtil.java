package window;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StyleUtil {

    private static final Pattern RGB = Pattern.compile("rgb\\s*\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)");

    public static Color parseColor(String style) {
        if (style == null || style.isEmpty()) return null;
        String s = style.toLowerCase().replace(" ", "");

        Matcher m = RGB.matcher(s);
        if (m.find()) {
            return new Color(Integer.parseInt(m.group(1)),
                             Integer.parseInt(m.group(2)),
                             Integer.parseInt(m.group(3)));
        }
        if (s.contains("transparent")) return null;
        if (s.contains("lightblue"))   return new Color(173, 216, 230);
        if (s.contains("lightgrey") || s.contains("lightgray")) return Color.LIGHT_GRAY;
        if (s.contains("darkgrey") || s.contains("darkgray"))   return Color.DARK_GRAY;
        if (s.contains("blanchedalmond")) return new Color(255, 235, 205);
        if (s.contains("whitesmoke"))  return new Color(245, 245, 245);
        if (s.contains("black"))       return Color.BLACK;
        if (s.contains("white"))       return Color.WHITE;
        if (s.contains("red"))         return Color.RED;
        if (s.contains("green"))       return Color.GREEN;
        if (s.contains("blue"))        return Color.BLUE;
        if (s.contains("yellow"))      return Color.YELLOW;
        if (s.contains("orange"))      return Color.ORANGE;
        if (s.contains("purple"))      return new Color(128, 0, 128);
        if (s.contains("pink"))        return Color.PINK;
        if (s.contains("cyan"))        return Color.CYAN;
        if (s.contains("grey") || s.contains("gray")) return Color.GRAY;
        if (s.contains("chocolate"))   return new Color(210, 105, 30);
        if (s.contains("lime"))        return new Color(0, 255, 0);
        return null;
    }
}
