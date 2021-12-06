package dev.tomat.constellar.content.gui.classy;

import dev.tomat.common.utils.ColorUtils;
import dev.tomat.common.utils.MathUtils;
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

    public float ScaleX = 1f;
    public float ScaleY = 1f;

    public ClassyGuiButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public ClassyGuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        this.width = 200;
        this.height = 20;
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

        int i = getHoverState(hovered);

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);

        //noinspection EqualsBetweenInconvertibleTypes
        if (!Objects.equals(getClass(), GuiOptionSlider.class)) {
            if (hovered) {
                ScaleX = MathUtils.lerp(ScaleX, 1.05f, 0.25f);
                // ScaleY = MathUtils.lerp(ScaleY, 1.2f, 0.1f);
            } else {
                ScaleX = MathUtils.lerp(ScaleX, 1f, 0.25f);
                // ScaleY = MathUtils.lerp(ScaleY, 1f, 0.1f);
            }
        }

        int scaledWidth = (int)(width * ScaleX);
        int scaledHeight = (int)(height * ScaleY);
        float scaledX = xPosition + ((width - scaledWidth) / 2f);
        float scaledY = yPosition - ((height - scaledHeight) / 2f);

        drawTexturedModalRect(scaledX, scaledY, 0, 46 + i * 20, scaledWidth, scaledHeight);
        mouseDragged(mc, mouseX, mouseY);

        int color = 14737632;

        // Re-implementation of patch: https://github.com/MinecraftForge/MinecraftForge/blob/1.8.9/patches/minecraft/net/minecraft/client/gui/GuiButton.java.patch
        if (!ConstellarTweaker.LoadContext.standalone(ConstellarTweaker.Context)) {
            if (getPackagedFGColor() != 0)
                color = getPackagedFGColor();
            else if (!enabled)
                color = 10526880;
            else if (hovered)
                color = ColorUtils.colorToInt(223, 173, 255, 255);
        } else {
            if (!enabled)
                color = 10526880;
            else if (hovered)
                color = ColorUtils.colorToInt(223, 173, 255, 255);
        }

        drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, color);
    }
    protected int getHoverState(boolean mouseOver) {
        int i = 1;
        if (!this.enabled) {
            i = 0;
        } else if (mouseOver) {
            i = 2;
        }

        return i;
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

    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {}

    public void mouseReleased(int mouseX, int mouseY) {}

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    public boolean isMouseOver() {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY) {}

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