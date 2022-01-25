package dev.tomat.constellar.content.modules.world;

import dev.tomat.constellar.content.modules.StandardModule;
import net.minecraft.util.ResourceLocation;

public class BlockOutlineModule extends StandardModule {
    public BlockOutlineModule() {
        super(
                "modules.blockoutline.name",
                "modules.blockoutline.description",
                new ResourceLocation("constellar", "blockoutline")
        );
    }

    @Override
    public ResourceLocation getImageLocation() {
        return new ResourceLocation("constellar", "textures/gui/modules/blockoutline_icon.png");
    }
}
