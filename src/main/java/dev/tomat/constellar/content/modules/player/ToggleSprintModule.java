package dev.tomat.constellar.content.modules.player;

import dev.tomat.constellar.content.modules.StandardModule;
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
