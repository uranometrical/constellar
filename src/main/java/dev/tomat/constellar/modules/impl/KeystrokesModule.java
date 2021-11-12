package dev.tomat.constellar.modules.impl;

import net.minecraft.util.ResourceLocation;

public class KeystrokesModule extends StandardModule {
    public KeystrokesModule() {
        super(
                "modules.keystrokes.name",
                "modules.keystrokes.description",
                new ResourceLocation("constellar", "keystrokes")
        );
    }
}
