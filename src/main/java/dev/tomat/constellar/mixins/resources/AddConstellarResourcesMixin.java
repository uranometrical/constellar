package dev.tomat.constellar.mixins.resources;

import dev.tomat.constellar.ConstellarMain;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.InputStream;

@Mixin(DefaultResourcePack.class)
public abstract class AddConstellarResourcesMixin {
    // Scan for OUR files as well.
    @Inject(method = "getResourceStream", at = @At("HEAD"), cancellable = true)
    private void getResourceStream(ResourceLocation location, CallbackInfoReturnable<InputStream> cir) {
        InputStream ourStream = ConstellarMain.class.getResourceAsStream("/assets/" + location.getResourceDomain() + "/" + location.getResourcePath());

        // System.out.println(location.getResourceDomain());

        if (ourStream != null)
        {
            cir.setReturnValue(ourStream);
            cir.cancel();
        }
    }
}
