package io.github.steviegt6.constellar.mixins.gui.panorama;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public abstract class GuiMainMenuTest extends GuiScreen {
    @Shadow
    private String splashText;
    @Shadow
    private String openGLWarning1;
    @Shadow
    private String openGLWarning2;

    @Shadow
    protected abstract boolean func_183501_a();

    @Shadow
    private GuiScreen field_183503_M;
    @Shadow
    private int field_92022_t;
    @Shadow
    private int field_92021_u;
    @Shadow
    private int field_92024_r;
    @Shadow
    private int field_92020_v;
    @Shadow
    private int field_92019_w;
    @Shadow
    private float updateCounter;
    private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");

    //@Inject(method = "drawScreen", at = @At("HEAD"), cancellable = true)
    /*public void drawPanoramaScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_, CallbackInfo ci) {
        if (PanoramaContainer.canShowPanorama(this)) {
            GlStateManager.disableAlpha();
            PanoramaContainer.renderSkybox(this, p_drawScreen_3_);
            GlStateManager.enableAlpha();
            Tessellator lvt_4_1_ = Tessellator.getInstance();
            WorldRenderer lvt_5_1_ = lvt_4_1_.getWorldRenderer();
            int lvt_6_1_ = 274;
            int lvt_7_1_ = width / 2 - lvt_6_1_ / 2;
            int lvt_8_1_ = 30;
            drawGradientRect(0, 0, width, height, -2130706433, 16777215);
            drawGradientRect(0, 0, width, height, 0, -2147483648);
            mc.getTextureManager().bindTexture(minecraftTitleTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            if ((double) updateCounter < 1.0E-4D) {
                drawTexturedModalRect(lvt_7_1_ + 0, lvt_8_1_ + 0, 0, 0, 99, 44);
                drawTexturedModalRect(lvt_7_1_ + 99, lvt_8_1_ + 0, 129, 0, 27, 44);
                drawTexturedModalRect(lvt_7_1_ + 99 + 26, lvt_8_1_ + 0, 126, 0, 3, 44);
                drawTexturedModalRect(lvt_7_1_ + 99 + 26 + 3, lvt_8_1_ + 0, 99, 0, 26, 44);
                drawTexturedModalRect(lvt_7_1_ + 155, lvt_8_1_ + 0, 0, 45, 155, 44);
            } else {
                drawTexturedModalRect(lvt_7_1_ + 0, lvt_8_1_ + 0, 0, 0, 155, 44);
                drawTexturedModalRect(lvt_7_1_ + 155, lvt_8_1_ + 0, 0, 45, 155, 44);
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate((float) (width / 2 + 90), 70.0F, 0.0F);
            GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
            float lvt_9_1_ = 1.8F - MathHelper.abs(MathHelper.sin((float) (Minecraft.getSystemTime() % 1000L) / 1000.0F * 3.1415927F * 2.0F) * 0.1F);
            lvt_9_1_ = lvt_9_1_ * 100.0F / (float) (fontRendererObj.getStringWidth(splashText) + 32);
            GlStateManager.scale(lvt_9_1_, lvt_9_1_, lvt_9_1_);
            drawCenteredString(fontRendererObj, splashText, 0, -8, -256);
            GlStateManager.popMatrix();
            String lvt_10_1_ = "Minecraft 1.8.9";
            if (mc.isDemo()) {
                lvt_10_1_ = lvt_10_1_ + " Demo";
            }

            drawString(fontRendererObj, lvt_10_1_, 2, height - 10, -1);
            String lvt_11_1_ = "Copyright Mojang AB. Do not distribute!";
            drawString(fontRendererObj, lvt_11_1_, width - fontRendererObj.getStringWidth(lvt_11_1_) - 2, height - 10, -1);
            if (openGLWarning1 != null && openGLWarning1.length() > 0) {
                drawRect(field_92022_t - 2, field_92021_u - 2, field_92020_v + 2, field_92019_w - 1, 1428160512);
                drawString(fontRendererObj, openGLWarning1, field_92022_t, field_92021_u, -1);
                drawString(fontRendererObj, openGLWarning2, (width - field_92024_r) / 2, ((GuiButton) buttonList.get(0)).yPosition - 12, -1);
            }

            super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
            if (func_183501_a()) {
                field_183503_M.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
            }

            ci.cancel();
        }
    }*/
}
