package dev.tomat.constellar.mixins.resources;

import dev.tomat.constellar.content.resources.ConstellarPack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Minecraft.class)
public abstract class RegisterConstellarPack {
    @Shadow @Final private List<IResourcePack> defaultResourcePacks;

    @Inject(method = "startGame", at = @At("HEAD"))
    public void startGame(CallbackInfo ci) {
        defaultResourcePacks.add(new ConstellarPack());
    }
}
