package dev.tomat.constellar.mixins.keybinds;

import dev.tomat.constellar.Constellar;
import dev.tomat.constellar.core.modules.ModuleNotFoundException;
import dev.tomat.constellar.core.modules.ModuleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyBinding.class)
public class KeyPressMixin {
    @Inject(method = "setKeyBindState", at = @At("HEAD"))
    private static void preSetKeyBindState(int keyCode, boolean pressed, CallbackInfo ci) throws ModuleNotFoundException {
        Minecraft mc = Minecraft.getMinecraft();
        // check if the W key is being pressed, and if so set the sprint key to be pressed
        // worked first try somehow?
        // togglesprint!
        if (!Constellar.Modules.getModule(ModuleType.ToggleSprint).isDisabled() && keyCode == mc.gameSettings.keyBindForward.getKeyCode()) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
        }
    }
}
