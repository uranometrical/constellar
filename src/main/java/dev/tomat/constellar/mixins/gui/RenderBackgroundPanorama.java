package dev.tomat.constellar.mixins.gui;

import dev.tomat.constellar.content.gui.BackgroundPanorama;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public abstract class RenderBackgroundPanorama extends Gui implements GuiYesNoCallback {
    @Shadow public int height;
    @Shadow public int width;
    @Shadow protected Minecraft mc;
    public float PartialTicks;

    @Inject(method = "drawScreen", at = @At("TAIL"))
    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (BackgroundPanorama.Instance == null)
            return;

        BackgroundPanorama.Instance.Timer++;

        PartialTicks = partialTicks;
    }

    /**
     * @author Tomat
     */
    @Overwrite
    public void drawBackground(int tint) {
        GlStateManager.disableLighting();
        GlStateManager.disableFog();

        if (BackgroundPanorama.Instance == null)
        {
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();

            mc.getTextureManager().bindTexture(optionsBackground);

            GlStateManager.color(1f, 1f, 1f, 1f);

            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldrenderer.pos(0D, height, 0D).tex(0D, (float)height / 32f + (float)tint).color(64, 64, 64, 255).endVertex();
            worldrenderer.pos(width, height, 0D).tex((float)width / 32f, (float)height / 32f + (float)tint).color(64, 64, 64, 255).endVertex();
            worldrenderer.pos(width, 0D, 0D).tex((float)width / 32f, tint).color(64, 64, 64, 255).endVertex();
            worldrenderer.pos(0D, 0D, 0D).tex(0D, tint).color(64, 64, 64, 255).endVertex();
            tessellator.draw();

            return;
        }

        BackgroundPanorama.Instance.render(PartialTicks);

        if (getClass().isAssignableFrom(GuiMainMenu.class))
            return;

        this.drawGradientRect(0, 0, this.width, this.height, -2130706433, 16777215);
        this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
    }

    @Inject(method = "setWorldAndResolution", at = @At("TAIL"))
    public void setWorldAndResolution(Minecraft mc, int width, int height, CallbackInfo ci) {
        if (BackgroundPanorama.Instance != null)
        {
            BackgroundPanorama.Instance.Width = width;
            BackgroundPanorama.Instance.Height = height;
        }
    }
}
