package dev.tomat.constellar.content.modules.ui;

import dev.tomat.common.utils.ColorUtils;
import dev.tomat.constellar.content.gui.GuiUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class Keystroke {
    public static final int Size = 20;

    public Minecraft mc;

    public Keyboard key;

    private int xPosition;
    private int yPosition;

    public boolean pressed = false;

    public Keystroke(Minecraft mc, Keyboard key, int xPosition, int yPosition) {
        this.mc = mc;
        this.key = key;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public void draw() {
        if (pressed) {
            GuiUtils.drawRectNormal(
                    xPosition,
                    yPosition,
                    Size,
                    Size,
                    ColorUtils.colorToInt(130, 130, 130, 100)
            );
        }
        else {
            GuiUtils.drawRectNormal(
                    xPosition,
                    yPosition,
                    Size,
                    Size,
                    ColorUtils.colorToInt(40, 40, 40, 100)
            );
        }

        String keyString = key.toString();

        mc.fontRendererObj.drawStringWithShadow(
                keyString,
                xPosition + (Size / 2f) - mc.fontRendererObj.getStringWidth(keyString) / 2f,
                yPosition - (Size / 2f),
                ColorUtils.White
        );
    }

    public void trigger(boolean pressed) {
        this.pressed = pressed;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
        // seralize here?
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
        // seralize here?
    }
}
