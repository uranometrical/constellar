package dev.tomat.constellar.mixins.gui.ingame;

import dev.tomat.constellar.Constellar;
import dev.tomat.constellar.content.modules.UiModule;
import dev.tomat.constellar.core.modules.IModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class RenderUiModules extends Gui {
    @Shadow @Final private Minecraft mc;

    @Inject(method = "renderGameOverlay", at = @At("HEAD"))
    public void preRenderGameOverlay(float partialTicks, CallbackInfo ci) {
        mc.mcProfiler.startSection("constellarModules");

        for (IModule module : Constellar.Modules.getModules().values()) {
            if (module extends UiModule) {

            }
        }

        this.mc.mcProfiler.endSection();
    }
}
