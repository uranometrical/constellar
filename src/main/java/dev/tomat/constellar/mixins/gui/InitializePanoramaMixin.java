package dev.tomat.constellar.mixins.gui;

import dev.tomat.constellar.gui.BackgroundPanorama;
import net.minecraft.client.Minecraft;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.util.IThreadListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class InitializePanoramaMixin implements IThreadListener, IPlayerUsage {
    @Inject(method = "startGame", at = @At("TAIL"))
    public void startGame(CallbackInfo ci) {
        BackgroundPanorama.Instance = new BackgroundPanorama();
    }
}
