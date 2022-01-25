package dev.tomat.constellar.mixins.gui.resourcepack;

import dev.tomat.constellar.content.gui.resourcepack.ResourcePackScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiOptions.class)
public class SwapResourcePackScreen extends GuiScreen {
    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    public void preActionPerformed(GuiButton button, CallbackInfo ci) {
        if (button.enabled) {
            if (button.id == 105)
            {
                mc.gameSettings.saveOptions();
                mc.displayGuiScreen(new ResourcePackScreen((GuiOptions) (Object) this));
                ci.cancel();
            }
        }
    }
}
