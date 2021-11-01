package dev.tomat.constellar.mixin.gui;

import dev.tomat.constellar.ConstellarConstants;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// probably an entirely better way to do this
@Mixin(GuiMainMenu.class)
public abstract class GuiMainMenuMixin extends GuiScreen implements GuiYesNoCallback {
    private static final ResourceLocation titleTexture = new ResourceLocation("constellar", "textures/gui/title/constellar.png");

    @Inject(method = "drawScreen", at = @At("TAIL"))
    public void drawScreen(int unknown1, int unknown2, float unknown3, CallbackInfo ci) {
        this.mc.getTextureManager().bindTexture(titleTexture);

        drawString(fontRendererObj, ConstellarConstants.NAME + " v" + ConstellarConstants.VERSION, 2, height - 20, -1);
    }
}
