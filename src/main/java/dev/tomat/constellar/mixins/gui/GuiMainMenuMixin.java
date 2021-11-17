package dev.tomat.constellar.mixins.gui;

import dev.tomat.constellar.ConstellarMain;
import dev.tomat.constellar.gui.BackgroundPanorama;
import dev.tomat.constellar.utilities.ColorUtils;
import dev.tomat.constellar.utilities.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public abstract class GuiMainMenuMixin extends GuiScreen implements GuiYesNoCallback {
    @Shadow private float updateCounter;

    @Shadow private String splashText;

    @Shadow private String openGLWarning1;
    @Shadow private int field_92022_t;
    @Shadow private int field_92021_u;
    @Shadow private int field_92020_v;
    @Shadow private int field_92019_w;
    @Shadow private int field_92024_r;
    @Shadow private String openGLWarning2;

    private static final ResourceLocation ConstellarTitle = new ResourceLocation("constellar", "textures/gui/title/constellar.png");

    /**
     * @author Tomat
     */
    @Overwrite
    public void drawScreen(int unknown1, int unknown2, float unknown3) {
        GlStateManager.disableAlpha();

        if (BackgroundPanorama.Instance != null)
            BackgroundPanorama.Instance.render(unknown3);

        GlStateManager.enableAlpha();

        int i = 274;
        drawGradientRect(0, 0, width, height, -2130706433, 16777215);
        drawGradientRect(0, 0, width, height, 0, Integer.MIN_VALUE);

        int weirdWidthStuff = width / 2 - 274 / 2;
        int probablyHeight = 30;

        mc.getTextureManager().bindTexture(ConstellarTitle);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if ((double)updateCounter >= 1.0E-4D) {
            drawTexturedModalRect(weirdWidthStuff - 13, probablyHeight, 0, 0, 155, 44);
            drawTexturedModalRect(weirdWidthStuff + 142, probablyHeight, 0, 44, 154, 44);
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(width / 2F, probablyHeight, 0.0F);
        GlStateManager.rotate(0F, 0.0F, 0.0F, 1.0F);
        //float splashScale = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * 3.1415927F * 2.0F) * 0.1F);
        //splashScale = splashScale * 100.0F / (float)(fontRendererObj.getStringWidth(splashText) + 32);
        //GlStateManager.scale(splashScale, splashScale, 0F);
        TextUtils.drawCenteredStringWithBorder(mc, splashText, 0, 46, ColorUtils.colorToInt(223, 173, 255, 255));
        GlStateManager.popMatrix();

        TextUtils.drawStringWithBorder(mc, "Minecraft 1.8.9", 2, height - 10, -1);
        String s1 = "Copyright Mojang AB. Do not distribute!";
        TextUtils.drawStringWithBorder(mc, s1, width - fontRendererObj.getStringWidth(s1) - 2, height - 10, -1);
        TextUtils.drawStringWithBorder(mc, ConstellarMain.ClientNameReadable + " v" + ConstellarMain.ClientVersion, 2, height - 20, -1);

        if (openGLWarning1 != null && openGLWarning1.length() > 0)
        {
            drawRect(field_92022_t - 2, field_92021_u - 2, field_92020_v + 2, field_92019_w - 1, 1428160512);
            drawString(fontRendererObj, openGLWarning1, field_92022_t, field_92021_u, -1);
            drawString(fontRendererObj, openGLWarning2, (width - field_92024_r) / 2, ((GuiButton)buttonList.get(0)).yPosition - 12, -1);
        }

        super.drawScreen(unknown1, unknown2, unknown3);

        //if (func_183501_a())
        //    field_183503_M.drawScreen(unknown1, unknown2, unknown3);
    }

    @Inject(method = "updateScreen", at = @At("HEAD"))
    public void updateScreen(CallbackInfo ci) {
        if (BackgroundPanorama.Instance != null) {
            BackgroundPanorama.Instance.Height = height;
            BackgroundPanorama.Instance.Width = width;
            BackgroundPanorama.Instance.Timer++;
        }
    }
}
