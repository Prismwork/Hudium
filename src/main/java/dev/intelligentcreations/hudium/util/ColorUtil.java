package dev.intelligentcreations.hudium.util;

import java.awt.*;

public final class ColorUtil {
    private ColorUtil() {}

    public static int convertColor(String hexColor) {
        Color color;
        if (hexColor != null) {
            try {
                color = Color.decode("#" + hexColor);
            }
            catch (NumberFormatException e) {
                color = Color.WHITE;
            }
        } else {
            color = Color.WHITE;
        }

        int redValue = color.getRed();
        int greenValue = color.getGreen();
        int blueValue = color.getBlue();

        return (256 * 256 * redValue) + (256 * greenValue) + blueValue;
    }
}
