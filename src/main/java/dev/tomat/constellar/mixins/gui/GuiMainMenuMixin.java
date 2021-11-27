package dev.tomat.constellar.mixins.gui;

import com.google.common.base.Strings;
import dev.tomat.common.reflection.FailedInvocationObject;
import dev.tomat.common.reflection.Reflector;
import dev.tomat.constellar.Constellar;
import dev.tomat.constellar.gui.BackgroundPanorama;
import dev.tomat.constellar.launch.ConstellarTweaker;
import dev.tomat.common.utils.ColorUtils;
import dev.tomat.common.utils.TextUtils;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Mixin(GuiMainMenu.class)
public abstract class GuiMainMenuMixin extends GuiScreen implements GuiYesNoCallback {
    @Shadow private GuiButton realmsButton;
    private static final ResourceLocation ConstellarTitle = new ResourceLocation("constellar", "textures/gui/title/constellar.png");

    @Shadow private float updateCounter;

    @Shadow private String splashText;

    @Shadow private String openGLWarning1;
    @Shadow private int field_92022_t;
    @Shadow private int field_92021_u;
    @Shadow private int field_92020_v;
    @Shadow private int field_92019_w;
    @Shadow private int field_92024_r;
    @Shadow private String openGLWarning2;

    /**
     * @author Tomat
     */
    @Overwrite
    public void drawScreen(int unknown1, int unknown2, float unknown3) {
        String copyrightText = "Copyright Mojang AB. Do not distribute!";

        GlStateManager.disableAlpha();

        if (BackgroundPanorama.Instance != null)
            BackgroundPanorama.Instance.render(unknown3);

        GlStateManager.enableAlpha();

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

        TextUtils.drawCenteredStringWithBorder(mc, splashText, 0, 46, ColorUtils.colorToInt(223, 173, 255, 255));
        GlStateManager.popMatrix();

        TextUtils.drawStringWithBorder(mc, copyrightText, width - fontRendererObj.getStringWidth(copyrightText) - 2, height - 10, -1);

        // Reimplementation of Forge patch: https://github.com/MinecraftForge/MinecraftForge/blob/1.8.9/patches/minecraft/net/minecraft/client/gui/GuiMainMenu.java.patch#L30
        List<String> brandings = getBrandingText();
        for (int i = 0; i < brandings.size(); i++) {
            String branding = brandings.get(i);

            if (!Strings.isNullOrEmpty(branding))
                TextUtils.drawStringWithBorder(mc, branding, 2, height - ((i + 1) * 10), -1);
        }

        if (openGLWarning1 != null && openGLWarning1.length() > 0)
        {
            drawRect(field_92022_t - 2, field_92021_u - 2, field_92020_v + 2, field_92019_w - 1, 1428160512);
            drawString(fontRendererObj, openGLWarning1, field_92022_t, field_92021_u, -1);
            drawString(fontRendererObj, openGLWarning2, (width - field_92024_r) / 2, (buttonList.get(0)).yPosition - 12, -1);
        }

        super.drawScreen(unknown1, unknown2, unknown3);
    }

    @Inject(method = "updateScreen", at = @At("HEAD"))
    public void updateScreen(CallbackInfo ci) {
        if (BackgroundPanorama.Instance != null) {
            BackgroundPanorama.Instance.Height = height;
            BackgroundPanorama.Instance.Width = width;
            BackgroundPanorama.Instance.Timer++;
        }
    }

    public List<String> getBrandingText() {
        List<String> brandings = new ArrayList<>();

        brandings.add("Minecraft 1.8.9");
        brandings.add(Constellar.ClientNameReadable + " v" + Constellar.ClientVersion);

        if (!ConstellarTweaker.LoadContext.standalone(ConstellarTweaker.Context))
            brandings.addAll(getForgeBrandings());

        return brandings;
    }

    @SuppressWarnings("unchecked")
    private List<String> getForgeBrandings() {
        Reflector reflector = Constellar.REFLECTOR;
        Class<?> clazz = reflector.getClass("net.minecraftforge.fml.common.FMLCommonHandler");

        if (clazz == null)
            return new ArrayList<>();

        Method getInstance = reflector.getMethod(clazz, "instance");
        Method getBrandings = reflector.getMethod(clazz, "getBrandings", boolean.class);

        if (getInstance == null || getBrandings == null)
            return new ArrayList<>();

        Object fmlCommonHandlerInstance = reflector.invokeMethod(getInstance, null);

        if (fmlCommonHandlerInstance instanceof FailedInvocationObject)
            return new ArrayList<>();

        Object list = reflector.invokeMethod(getBrandings, fmlCommonHandlerInstance, false);

        if (list instanceof FailedInvocationObject)
            return new ArrayList<>();

        return (List<String>) list;
    }

    @Inject(method = "addSingleplayerMultiplayerButtons", at = @At("TAIL"))
    private void removeRealmsButton(int y, int offset, CallbackInfo ci) {
        // this.buttonList.remove(realmsButton); // no more realms button oh noo
    }
}
