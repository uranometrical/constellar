package io.github.steviegt6.constellar.mixins.gui;

import io.github.steviegt6.constellar.ConstellarMain;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public abstract class GuiMainMenuMixin extends GuiScreen implements GuiYesNoCallback {
    @Shadow private ResourceLocation backgroundTexture;

    @Shadow private int panoramaTimer;

    @Inject(method = "drawScreen", at = @At("TAIL"))
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_, CallbackInfo ci) {
        // We need to know when the main menu is loaded to ensure loading of other processes is safe.
        ConstellarMain.MainMenuLoaded = true;
        panoramaTimer = ConstellarMain.panoramaTimer;

        drawString(fontRendererObj, ConstellarMain.ClientNameReadable + " v" + ConstellarMain.ClientVersion, 2, height - 20, -1);
    }

    @Inject(method = "initGui", at = @At("TAIL"))
    public void extractBackground(CallbackInfo ci) {
        ConstellarMain.backgroundTexture = backgroundTexture;
    }
}
