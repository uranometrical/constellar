package dev.tomat.constellar.modules.impl.ui;

import dev.tomat.constellar.modules.impl.StandardModule;
import net.minecraft.util.ResourceLocation;

public class KeystrokesModule extends StandardModule {
    public KeystrokesModule() {
        super(
                "modules.keystrokes.name",
                "modules.keystrokes.description",
                new ResourceLocation("constellar", "keystrokes")
        );
    }

    @Override
    public ResourceLocation getImageLocation() {
        return new ResourceLocation("constellar", "textures/gui/modules/keystrokes_icon.png");
    }
}
