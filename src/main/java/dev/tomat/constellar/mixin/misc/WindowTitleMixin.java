package dev.tomat.constellar.mixin.misc;

import dev.tomat.constellar.ConstellarConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.util.IThreadListener;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Admittedly arrogant of us to change the display.
// TODO: Injection here is not necessary, ported from standalone client.
@Mixin(Minecraft.class)
public abstract class WindowTitleMixin implements IThreadListener, IPlayerUsage {
    @Shadow private static Minecraft theMinecraft;

    @Inject(method = "createDisplay", at = @At("TAIL"))
    private void injectWindowTitle(CallbackInfo ci) {
        //Display.setTitle(ConstellarConstants.NAME + " v" + ConstellarConstants.Version + " - " + theMinecraft.getVersion());
    }
}
