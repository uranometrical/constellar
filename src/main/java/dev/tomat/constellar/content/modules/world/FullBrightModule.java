package dev.tomat.constellar.content.modules.world;

import dev.tomat.constellar.content.modules.StandardModule;
import dev.tomat.constellar.core.modules.ModuleStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class FullBrightModule extends StandardModule {
    public FullBrightModule() {
        super(
                "modules.fullbright.name",
                "modules.fullbright.description",
                new ResourceLocation("constellar", "fullbright")
        );
    }

    private float previousGammaValue = 1f;

    @Override
    public void toggle() {
        if (Status == ModuleStatus.FORCE_DISABLED)
            return;

        if (Status == ModuleStatus.DISABLED) {
            previousGammaValue = Minecraft.getMinecraft().gameSettings.gammaSetting;
            Minecraft.getMinecraft().gameSettings.gammaSetting = 32f;
            Status = ModuleStatus.ENABLED;
        }
        else {
            Minecraft.getMinecraft().gameSettings.gammaSetting = previousGammaValue;
            Status = ModuleStatus.DISABLED;
        }
    }
}
