package dev.tomat.constellar.utilities;

public class ColorUtils {
    public static int colorToInt(int r, int g, int b, int a) {
        // Thanks Ozzatron

        int redInt = (r & 0xFF) << 16;
        int greenInt = (g & 0xFF) << 8;
        int blueInt = (b & 0xFF);
        int alphaInt = (a & 0xFF) << 24;
        return redInt + greenInt + blueInt + alphaInt;
    }
}
