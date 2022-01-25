package dev.tomat.constellar.content.gui.classy;

import dev.tomat.common.utils.ColorUtils;
import dev.tomat.common.utils.MathUtils;
import dev.tomat.constellar.content.gui.GuiUtils;
import dev.tomat.constellar.launch.ConstellarTweaker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Objects;

public abstract class ClassyGuiButton extends Gui {
    public final ResourceLocation Buttons = new ResourceLocation("textures/gui/buttons.png");

    public int id;
    public boolean visible;
    protected boolean hovered;

    public int xPosition;
    public int yPosition;
    protected int width;
    protected int height;

    public String displayString;
    public boolean enabled;

    public float ButtonWidthScale = 1f;

    public ClassyGuiButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, GuiUtils.DefaultButtonWidth, GuiUtils.DefaultButtonHeight, buttonText);
    }

    public ClassyGuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (!visible)
            return;

        FontRenderer fontrenderer = mc.fontRendererObj;

        mc.getTextureManager().bindTexture(Buttons);

        GlStateManager.color(1F, 1F, 1F, 1F);

        hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;

        // todo: hoverstate enum
        int hoverState = getHoverState(hovered);

        GuiUtils.blend();

        if (hovered) {
            ButtonWidthScale = MathUtils.lerp(ButtonWidthScale, 1.05f, 0.25f);
        } else {
            ButtonWidthScale = MathUtils.lerp(ButtonWidthScale, 1f, 0.25f);
        }

        int scaledWidth = (int)(width * ButtonWidthScale);
        float scaledX = xPosition + ((width - scaledWidth) / 2f);

        // todo: make 46 a constant or something
        drawTexturedModalRect(scaledX, yPosition, 0, 46 + hoverState * height, scaledWidth, height);
        mouseDragged(mc, mouseX, mouseY);

        int color = ColorUtils.colorToInt(224, 224, 224, 255);

        // Re-implementation of patch: https://github.com/MinecraftForge/MinecraftForge/blob/1.8.9/patches/minecraft/net/minecraft/client/gui/GuiButton.java.patch
        if (!ConstellarTweaker.LoadContext.standalone(ConstellarTweaker.Context)) {
            if (getPackagedFGColor() != 0)
                color = getPackagedFGColor();
            else if (!enabled)
                color = ColorUtils.colorToInt(160, 160, 160, 255);
            else if (hovered)
                color = ColorUtils.colorToInt(223, 173, 255, 255);
        } else {
            if (!enabled)
                color = ColorUtils.colorToInt(160, 160, 160, 255);
            else if (hovered)
                color = ColorUtils.colorToInt(223, 173, 255, 255);
        }

        drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, color);
    }

    // todo: hoverstate enum
    protected int getHoverState(boolean mouseOver) {
        int state = 1;
        if (!this.enabled) {
            state = 0;
        } else if (mouseOver) {
            state = 2;
        }

        return state;
    }

    private int getPackagedFGColor() {
        Field pFGC;

        try {
            pFGC = GuiButton.class.getField("packagedFGColour");
        } catch (NoSuchFieldException e) {
            return 0;
        }

        try {
            return (int) pFGC.get(this);
        } catch (IllegalAccessException e) {
            return 0;
        }
    }

    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {

    }

    public void mouseReleased(int mouseX, int mouseY) {

    }

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    public boolean isMouseOver() {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY) {

    }

    public void playPressSound(SoundHandler soundHandlerIn) {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1F));
    }

    public int getButtonWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}