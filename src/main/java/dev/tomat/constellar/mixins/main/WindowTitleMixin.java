package dev.tomat.constellar.mixins.main;

import dev.tomat.constellar.ConstellarMain;
import net.minecraft.client.Minecraft;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.util.IThreadListener;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class WindowTitleMixin implements IThreadListener, IPlayerUsage {
    @Shadow
    private static Minecraft theMinecraft;

    @Inject(method = "createDisplay", at = @At("RETURN"))
    private void injectWindowTitle(CallbackInfo ci) {
        // Constellar 0.1.0-alpha - 1.8.9
        Display.setTitle(ConstellarMain.ClientNameReadable + " " + ConstellarMain.ClientVersion + " - " + theMinecraft.getVersion());
    }
}
