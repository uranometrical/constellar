package dev.tomat.constellar.core.modules.impl.player;

import dev.tomat.constellar.core.modules.impl.StandardModule;
import net.minecraft.util.ResourceLocation;

public class ToggleSprintModule extends StandardModule {
    public ToggleSprintModule() {
        super(
                "modules.togglesprint.name",
                "modules.togglesprint.description",
                new ResourceLocation("constellar", "togglesprint")
        );
    }

    @Override
    public ResourceLocation getImageLocation() {
        return new ResourceLocation("constellar", "textures/gui/modules/placeholder_icon.png");
    }
}
