package dev.tomat.constellar.utilities;

public class ColorUtils {
    public static int colorToInt(int r, int g, int b, int a) {
        return (r << 24) + (g << 16) + (b << 8) + (a);
    }
}
