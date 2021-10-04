package io.github.steviegt6.constellar.mixins.main;

import io.github.steviegt6.constellar.ConstellarMain;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class WindowTitleMixin {
    @Shadow
    private static Minecraft theMinecraft;

    @Inject(method = "createDisplay", at = @At("RETURN"))
    private void injectWindowTitle(CallbackInfo ci) {
        // Constellar 0.1.0-alpha - 1.8.9
        Display.setTitle(ConstellarMain.ClientNameReadable + " " + ConstellarMain.ClientVersion + " - " + theMinecraft.getVersion());
    }
}
