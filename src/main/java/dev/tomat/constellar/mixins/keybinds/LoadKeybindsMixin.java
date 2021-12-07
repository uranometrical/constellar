package dev.tomat.constellar.mixins.keybinds;

import dev.tomat.constellar.Constellar;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(GameSettings.class)
public class LoadKeybindsMixin {
    @Shadow
    public KeyBinding[] keyBindings;

    // both GameSettings ctor
    @Inject(method = "<init>*", at = @At("RETURN"))
    public void constructorHead(CallbackInfo ci) {
        ArrayList<KeyBinding> keyBindingsList = new ArrayList<>(Arrays.asList(keyBindings));
        keyBindingsList.addAll(Constellar.Keybindings.keybinds);
        keyBindings = keyBindingsList.toArray(new KeyBinding[0]);
    }
}
