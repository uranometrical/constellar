package dev.tomat.constellar.modules.impl.world;

import dev.tomat.constellar.modules.impl.StandardModule;
import net.minecraft.util.ResourceLocation;

public class BlockOutlineModule extends StandardModule {
    public BlockOutlineModule() {
        super(
                "modules.blockoutline.name",
                "modules.blockoutline.description",
                new ResourceLocation("constellar", "blockoutline")
        );
    }
}
