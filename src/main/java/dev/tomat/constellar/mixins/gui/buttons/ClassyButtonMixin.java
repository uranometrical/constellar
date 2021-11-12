package dev.tomat.constellar.mixins.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiButton.class)
public abstract class ClassyButtonMixin extends Gui {
    public final ResourceLocation Buttons = new ResourceLocation("textures/gui/buttons.png");

    @Shadow public boolean visible;
    @Shadow @Final protected static ResourceLocation buttonTextures;
    @Shadow protected boolean hovered;
    @Shadow public int xPosition;
    @Shadow public int yPosition;
    @Shadow protected int width;
    @Shadow protected int height;

    @Shadow protected abstract int getHoverState(boolean mouseOver);

    @Shadow protected abstract void mouseDragged(Minecraft mc, int mouseX, int mouseY);

    @Shadow public String displayString;
    @Shadow public boolean enabled;

    public float ScaleX = 1f;
    public float ScaleY = 1f;

    /**
     * @author Tomat
     */
    @Overwrite
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (!visible)
            return;

        FontRenderer fontrenderer = mc.fontRendererObj;

        mc.getTextureManager().bindTexture(Buttons);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;

        int i = getHoverState(hovered);

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);

        drawTexturedModalRect(xPosition, yPosition, 0, 46 + i * 20, width / 2, height);
        drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2, 46 + i * 20, width / 2, height);
        mouseDragged(mc, mouseX, mouseY);
        
        int color = 14737632;

        if (!enabled)
            color = 10526880;
        else if (hovered)
            color = 16777120;

        drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, color);
    }
}
