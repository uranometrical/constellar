package dev.tomat.constellar.content.gui;

import net.minecraft.client.gui.Gui;

public class GuiUtils {
    public static void drawRectNormal(int x, int y, int width, int height, int color, PosMode mode) {
        switch (mode) {
            case CENTER:
                Gui.drawRect(x - (width / 2), y - (height / 2), x + (width / 2), y + (width / 2), color);
                break;

            case UNBIASED:
                Gui.drawRect(x, y, x + width, y + height, color);
                break;
        }
    }

    public enum PosMode {
        CENTER,
        UNBIASED
    }
}
