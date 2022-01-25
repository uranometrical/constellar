package dev.tomat.common.utils;

import net.minecraft.client.Minecraft;

public class TextUtils {
    // Color formatting tokens (no stylized mark-up tokens!).
    public static final char[] colorTokens = {
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            'a',
            'b',
            'c',
            'd',
            'e',
            'f'
    };

    public static final String tokenIndicator = "\u00a7";

    public static final String resetToken = tokenIndicator + 'r';

    public static String cleanString(String string) {
        for (char token : colorTokens) {
            string = string.replace(tokenIndicator + token, resetToken).replace((tokenIndicator + token).toUpperCase(), resetToken);
        }

        return string;
    }

    public static void drawCenteredStringWithBorder(Minecraft minecraft, String string, int x, int y, int color) {
        drawStringWithBorder(minecraft, string, (x - minecraft.fontRendererObj.getStringWidth(string) / 2), y, color);
    }

    public static void drawStringWithBorder(Minecraft minecraft, String string, int x, int y, int color) {
        String clean = cleanString(string);

        drawStringBorder(minecraft, clean, x + 1, y);
        drawStringBorder(minecraft, clean, x - 1, y);
        drawStringBorder(minecraft, clean, x, y + 1);
        drawStringBorder(minecraft, clean, x, y - 1);

        minecraft.fontRendererObj.drawString(string, x, y, color);
    }

    public static void drawStringBorder(Minecraft minecraft, String string, int x, int y) {
        minecraft.fontRendererObj.drawString(cleanString(string), x, y, ColorUtils.colorToInt(0, 0, 0, 255));
    }
}
