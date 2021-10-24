package io.github.steviegt6.constellar.modules.impl;

import net.minecraft.util.ResourceLocation;

public class KeystrokesModule extends StandardModule {
    public KeystrokesModule(String key, String description) {
        super("constellar.modules.keystrokes", description, new ResourceLocation("constellar", "keystrokes"));
    }
}
