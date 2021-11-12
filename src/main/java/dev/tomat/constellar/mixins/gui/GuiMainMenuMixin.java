package dev.tomat.constellar.mixins.gui;

import dev.tomat.constellar.ConstellarMain;
import dev.tomat.constellar.IHateReflection;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Mixin(GuiMainMenu.class)
public abstract class GuiMainMenuMixin extends GuiScreen implements GuiYesNoCallback {
    @Shadow protected abstract void renderSkybox(int p_renderSkybox_1_, int p_renderSkybox_2_, float p_renderSkybox_3_);

    @Shadow private float updateCounter;

    @Shadow private String splashText;

    private static final ResourceLocation titleTexture = new ResourceLocation("constellar", "textures/gui/title/constellar.png");

    private String splashTextCache;

    @Inject(method = "drawScreen", at = @At("HEAD"))
    public void cacheSplashText(int unknown1, int unknown2, float unknown3, CallbackInfo ci) {
        // Stops splash text from drawing the first time.
        splashTextCache = splashText;
        splashText = "";
    }

    @Inject(method = "drawScreen", at = @At("TAIL"))
    public void drawScreen(int unknown1, int unknown2, float unknown3, CallbackInfo ci) {
        splashText = splashTextCache;

        int weirdWidthStuff = width / 2 - 274 / 2;
        int probablyHeight = 30;

        mc.getTextureManager().bindTexture(titleTexture);

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
        drawCenteredString(fontRendererObj, splashText, 0, 32, -256);
        GlStateManager.popMatrix();

        drawString(fontRendererObj, ConstellarMain.ClientNameReadable + " v" + ConstellarMain.ClientVersion, 2, height - 20, -1);
    }

    static {
        try {
            Field titleField;

            try {
                titleField = GuiMainMenu.class.getDeclaredField("minecraftTitleTextures");
            } catch (NoSuchFieldException e) {
                titleField = GuiMainMenu.class.getDeclaredField(IHateReflection.GuiMainMenuMinecraftTitleTextures);
            }

            titleField.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(titleField, titleField.getModifiers() & ~Modifier.FINAL);

            // Stop vanilla logo from drawing.
            titleField.set(null, new ResourceLocation("constellar", "textures/gui/title/empty.png"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // sucks to suck
            e.printStackTrace();
        }
    }
}
