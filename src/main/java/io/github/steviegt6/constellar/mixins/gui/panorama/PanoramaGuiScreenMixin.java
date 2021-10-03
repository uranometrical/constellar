package io.github.steviegt6.constellar.mixins.gui.panorama;

import io.github.steviegt6.constellar.ConstellarMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// This class injects these fields and methods into the base GuiScreen class to use later.
@Mixin(GuiScreen.class)
public abstract class PanoramaGuiScreenMixin extends Gui implements GuiYesNoCallback {
    @Shadow
    protected Minecraft mc;

    @Shadow
    public int width;

    @Shadow
    public int height;

    public int ourPanoramaTimer;

    @Inject(method = "updateScreen", at = @At("TAIL"))
    public void updatePanorama(CallbackInfo ci) {
        if (canShowPanorama())
            ourPanoramaTimer++;
    }

    @Inject(method = "drawScreen", at = @At("HEAD"))
    public void drawSkyboxRender(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_, CallbackInfo ci) {
        if (canShowPanorama())
            renderSkybox(p_drawScreen_3_);
    }

    //region Dreaded Minecraft De-comp Code

    private void drawPanorama(float p_drawPanorama_3_) {
        Tessellator lvt_4_1_ = Tessellator.getInstance();
        WorldRenderer lvt_5_1_ = lvt_4_1_.getWorldRenderer();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        int lvt_6_1_ = 8;

        for (int lvt_7_1_ = 0; lvt_7_1_ < lvt_6_1_ * lvt_6_1_; ++lvt_7_1_) {
            GlStateManager.pushMatrix();
            float lvt_8_1_ = ((float) (lvt_7_1_ % lvt_6_1_) / (float) lvt_6_1_ - 0.5F) / 64.0F;
            float lvt_9_1_ = ((float) (lvt_7_1_ / lvt_6_1_) / (float) lvt_6_1_ - 0.5F) / 64.0F;
            float lvt_10_1_ = 0.0F;
            GlStateManager.translate(lvt_8_1_, lvt_9_1_, lvt_10_1_);
            GlStateManager.rotate(MathHelper.sin(((float) ourPanoramaTimer + p_drawPanorama_3_) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-((float) ourPanoramaTimer + p_drawPanorama_3_) * 0.1F, 0.0F, 1.0F, 0.0F);

            for (int lvt_11_1_ = 0; lvt_11_1_ < 6; ++lvt_11_1_) {
                GlStateManager.pushMatrix();
                if (lvt_11_1_ == 1) {
                    GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (lvt_11_1_ == 2) {
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                }

                if (lvt_11_1_ == 3) {
                    GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (lvt_11_1_ == 4) {
                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (lvt_11_1_ == 5) {
                    GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                }

                mc.getTextureManager().bindTexture(ConstellarMain.titlePanoramaPaths[lvt_11_1_]);
                lvt_5_1_.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                int lvt_12_1_ = 255 / (lvt_7_1_ + 1);
                float lvt_13_1_ = 0.0F;
                lvt_5_1_.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, lvt_12_1_).endVertex();
                lvt_5_1_.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, lvt_12_1_).endVertex();
                lvt_5_1_.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, lvt_12_1_).endVertex();
                lvt_5_1_.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, lvt_12_1_).endVertex();
                lvt_4_1_.draw();
                GlStateManager.popMatrix();
            }

            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }

        lvt_5_1_.setTranslation(0.0D, 0.0D, 0.0D);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }

    private void rotateAndBlurSkybox() {
        mc.getTextureManager().bindTexture(ConstellarMain.backgroundTexture);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.colorMask(true, true, true, false);
        Tessellator lvt_2_1_ = Tessellator.getInstance();
        WorldRenderer lvt_3_1_ = lvt_2_1_.getWorldRenderer();
        lvt_3_1_.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        GlStateManager.disableAlpha();
        int lvt_4_1_ = 3;

        for (int lvt_5_1_ = 0; lvt_5_1_ < lvt_4_1_; ++lvt_5_1_) {
            float lvt_6_1_ = 1.0F / (float) (lvt_5_1_ + 1);
            int lvt_7_1_ = width;
            int lvt_8_1_ = height;
            float lvt_9_1_ = (float) (lvt_5_1_ - lvt_4_1_ / 2) / 256.0F;
            lvt_3_1_.pos(lvt_7_1_, lvt_8_1_, zLevel).tex(0.0F + lvt_9_1_, 1.0D).color(1.0F, 1.0F, 1.0F, lvt_6_1_).endVertex();
            lvt_3_1_.pos(lvt_7_1_, 0.0D, zLevel).tex(1.0F + lvt_9_1_, 1.0D).color(1.0F, 1.0F, 1.0F, lvt_6_1_).endVertex();
            lvt_3_1_.pos(0.0D, 0.0D, zLevel).tex(1.0F + lvt_9_1_, 0.0D).color(1.0F, 1.0F, 1.0F, lvt_6_1_).endVertex();
            lvt_3_1_.pos(0.0D, lvt_8_1_, zLevel).tex(0.0F + lvt_9_1_, 0.0D).color(1.0F, 1.0F, 1.0F, lvt_6_1_).endVertex();
        }

        lvt_2_1_.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }

    public void renderSkybox(float p_renderSkybox_3_) {
        mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        drawPanorama(p_renderSkybox_3_);
        rotateAndBlurSkybox();
        rotateAndBlurSkybox();
        rotateAndBlurSkybox();
        rotateAndBlurSkybox();
        rotateAndBlurSkybox();
        rotateAndBlurSkybox();
        rotateAndBlurSkybox();
        mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, mc.displayWidth, mc.displayHeight);
        float lvt_4_1_ = width > height ? 120.0F / (float) width : 120.0F / (float) height;
        float lvt_5_1_ = (float) height * lvt_4_1_ / 256.0F;
        float lvt_6_1_ = (float) width * lvt_4_1_ / 256.0F;
        int lvt_7_1_ = width;
        int lvt_8_1_ = height;
        Tessellator lvt_9_1_ = Tessellator.getInstance();
        WorldRenderer lvt_10_1_ = lvt_9_1_.getWorldRenderer();
        lvt_10_1_.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        lvt_10_1_.pos(0.0D, lvt_8_1_, zLevel).tex(0.5F - lvt_5_1_, 0.5F + lvt_6_1_).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        lvt_10_1_.pos(lvt_7_1_, lvt_8_1_, zLevel).tex(0.5F - lvt_5_1_, 0.5F - lvt_6_1_).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        lvt_10_1_.pos(lvt_7_1_, 0.0D, zLevel).tex(0.5F + lvt_5_1_, 0.5F - lvt_6_1_).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        lvt_10_1_.pos(0.0D, 0.0D, zLevel).tex(0.5F + lvt_5_1_, 0.5F + lvt_6_1_).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        lvt_9_1_.draw();
    }

    //endregion

    public boolean canShowPanorama() {
        return ConstellarMain.backgroundTexture != null && ConstellarMain.MainMenuLoaded && !(Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu);
    }
}
