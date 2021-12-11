package dev.tomat.constellar.content.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public abstract class GuiIconButton extends GuiButton {
    public static final int DefaultButtonIconSize = 16;

    public GuiIconButton(int buttonId, int xPos, int yPos)
    {
        super(buttonId, xPos, yPos, GuiUtils.DefaultButtonHeight, GuiUtils.DefaultButtonHeight, "");
    }

    public GuiIconButton(int buttonId, int xPos, int yPos, int width, int height)
    {
        super(buttonId, xPos, yPos, width, height, "");
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (!visible)
            return;

        boolean hovering = isHovering(mouseX, mouseY);

        mc.getTextureManager().bindTexture(getButtonTexture());
        GuiUtils.resetColor();
        GuiUtils.blend();

        drawButton(hovering);

        mc.getTextureManager().bindTexture(getIconTexture());
        GuiUtils.resetColor();

        int padding = (width - getIconSize()) / 2;

        drawTexturedModalRect(
                xPosition + padding,
                yPosition + padding,
                getSpreadsheetX(),
                getSpreadsheetY(),
                getIconSize(),
                getIconSize()
        );
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
    }

    public ResourceLocation getButtonTexture() {
        return GuiUtils.ButtonsTexture;
    }

    public void drawButton(boolean hovering) {
        drawTexturedModalRect(xPosition, yPosition, 0, GuiUtils.ButtonSpreadsheetY + (getHoverState(hovering) * height), width, height);
    }

    public abstract ResourceLocation getIconTexture();

    public int getIconSize() {
        return DefaultButtonIconSize;
    }

    protected abstract int getSpreadsheetX();

    protected abstract int getSpreadsheetY();
}
